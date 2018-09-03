# -*- mode: ruby -*-
# vi: set ft=ruby :

AWS_PROFILE         = "default" # used for Vagrant to launch EC2
ASSUME_ROLE_MODE    = true
APPNAME             = Pathname.new(Dir.getwd).basename.to_s # used for name tag
GUEST_SYNCED_FOLDER = "/vagrant"
INSTANCE_TYPE       = "t2.medium"
VOLUME_SIZE         = 10
AMI_ID              = "ami-725eae08" # bt-centos7-vagrant-docker"
INSTANCE_USER       = "centos"
EC2_KEYPAIR_NAME    = "vagrant-#{ENV['USERNAME'].downcase}"
PEM_KEY_PATH        = File.join(ENV['HOME'], "/.ssh/#{EC2_KEYPAIR_NAME}.pem").gsub('\\', '/')

#AMI_ID = "ami-40271156" # CentOS Linux 7 x86_64 HVM EBS
#AMI_ID = "ami-a8a8b4d3" # Amazon ECS optimized
#AMI_ID = "ami-22f1eb59" # bt-centos7-091217

require "json"

puts <<'EOH'
  _______   __   __   ________   ______      
/_______/\ /_/\ /_/\ /_______/\ /_____/\     
\::: _  \ \\:\ \\ \ \\::: _  \ \\:::_ \ \    
 \::(_)  \/_\:\ \\ \ \\::(_)  \ \\:\ \ \ \   
  \::  _  \ \\:\_/.:\ \\:: __  \ \\:\ \ \ \  
   \::(_)  \ \\ ..::/ / \:.\ \  \ \\:\/.:| | 
    \_______\/ \___/_(   \__\/\__\/ \____/_/ 

         Billtrust Vagrant AWS Docker

EOH
puts <<-EOH
EC2 Instance:   #{APPNAME}
Instance Type:  #{INSTANCE_TYPE}
PEM Key Path:   #{PEM_KEY_PATH}

EOH

if not File.exists?(PEM_KEY_PATH)
  puts <<-EOH
Can't find vagrant PEM key! Rather than use the shared btdev-ec2-keypair.pem,
you must generate a private PEM key for your vagrant use.  Go to the KeyPairs
section of the EC2 AWS console:
https://console.aws.amazon.com/ec2/v2/home?region=us-east-1#KeyPairs
And generate a new keypair.  Use the name format: vagrant-yourwindowsusername,
e.g. vagrant-dkerwin.  Place the .pem key in your ~/.ssh/ directory, e.g.
c:/users/dkerwin/.ssh/vagrant-dkerwin.pem
EOH
  exit(1)
end

if ARGV.length > 0
  firstarg = ARGV[0].downcase
else
  firstarg = ""
end

# only bother with the AWS creds when they are needed
if ['up', 'rsync-auto', 'destroy','ssh'].include?(firstarg)
  if ASSUME_ROLE_MODE
    puts "Generating AWS temp creds using local profile: #{AWS_PROFILE}"
    sts_response_string = `aws sts assume-role --role-arn arn:aws:iam::855446687578:role/bt-role-ops-developers --role-session-name "vagrant-#{APPNAME}-#{ENV['USERNAME']}" --profile #{AWS_PROFILE}`
    sts_response = JSON.parse(sts_response_string)
    aws_access_key    = sts_response["Credentials"]["AccessKeyId"]
    aws_secret_key    = sts_response["Credentials"]["SecretAccessKey"]
    aws_session_token = sts_response["Credentials"]["SessionToken"]
    if aws_access_key.length <= 0
      puts "AWS access key was not generated"
      exit(1)
    end
    puts "AWS temp creds generated (#{aws_access_key})"
  else
    puts <<-EOH
  Reading AWS creds directly from ~/.aws/credentials (profile #{AWS_PROFILE})
  If you are using a master account and need to assume a role, set ASSUME_ROLE_MODE = true
  in the Vagrantfile.
  EOH
    # grab the AWS key/secret from the AWS credentials file
    begin
      lines = File.read("#{ENV['HOME']}/.aws/credentials").lines
      idx = 0
      user_idx = 0
      lines.each do |line|
        if line == "[#{AWS_PROFILE}]"
          user_idx = idx
          break
        end
        idx = idx + 1
      end

      aws_access_key = lines[user_idx + 1].split('=')[1].strip()
      aws_secret_key = lines[user_idx + 2].split('=')[1].strip()
      puts "Read AWS creds from local profile (#{aws_access_key})"

    rescue => exception
      puts "Error reading AWS credentials: #{exception.message}"
      exit(1)
    end
  end
end

if not ENV['MSYSTEM'] == 'MINGW64'
  puts <<-EOH
  !!!!!!!
  WARNING: Vagrant does not appear to be running from within Git Bash.
           You may not be able to rsync.
  EOH
end

# auto install vagrant-aws plugin
unless Vagrant.has_plugin?('vagrant-aws')
  system('vagrant plugin install vagrant-aws') || exit!
  exit system('vagrant', *ARGV)
end

# auto install vagrant-docker-compose plugin
unless Vagrant.has_plugin?('vagrant-docker-compose')
  system('vagrant plugin install vagrant-docker-compose') || exit!
  exit system('vagrant', *ARGV)
end

Vagrant.configure(2) do |config|
  # https://github.com/mitchellh/vagrant/issues/3230#issuecomment-62588180
  ENV["VAGRANT_DETECTED_OS"] = ENV["VAGRANT_DETECTED_OS"].to_s + " cygwin"

  config.vm.boot_timeout = 600

  # auto install docker compose
  config.vm.provision :docker
  config.vm.provision :docker_compose

  config.vm.provision "shell",
    inline: "pip install boto3 iam-docker-run --upgrade"

  # vagrant box add dummy https://github.com/mitchellh/vagrant-aws/raw/master/dummy.box
  config.vm.box = "aws-box"
  config.vm.box_url = "https://github.com/mitchellh/vagrant-aws/raw/master/dummy.box"

  config.vm.synced_folder ".", GUEST_SYNCED_FOLDER, type: "rsync", 
  rsync__exclude: [".vscode/", "/src/.vs/"],
  rsync__args: ["--verbose", "--rsync-path='sudo rsync'", "--archive", "--delete", "-z"]
  # copy the host's .aws folder to the ec2 instance so that all the local aws profiles
  # are still available for use on the instance
  config.vm.synced_folder File.join(ENV['HOME'], "/.aws"), "/home/#{INSTANCE_USER}/.aws", type: "rsync"
  # IMPORTANT: due to windows, must use "vagrant rsync-auto" after "vagrant up" or it only initially syncs

  config.vm.provider :aws do |aws, override|
    # aws.aws_dir = File.join(ENV['HOME'], "/.aws")
    aws.access_key_id = aws_access_key
    aws.secret_access_key = aws_secret_key
    if aws_session_token
      aws.session_token = aws_session_token
    end
    aws.region = "us-east-1"
    
    override.ssh.private_key_path = PEM_KEY_PATH
    aws.keypair_name = EC2_KEYPAIR_NAME

    aws.ami = AMI_ID
    override.ssh.username = INSTANCE_USER

    aws.aws_profile = "bt-role-ec2-public"
    aws.instance_type = INSTANCE_TYPE
    aws.block_device_mapping = [{ 'DeviceName' => '/dev/sda1', 'Ebs.VolumeSize' => VOLUME_SIZE }]
    aws.ssh_host_attribute = :private_ip_address

    aws.security_groups = [
      "sg-51c8ec2f", # sg-ec2-private
      "sg-7d15000d", # bt-sg-ec2-private-internalnetwork
      "sg-310b2a4e", # bt-sg-admin-ssh
      "sg-83baf6f3" # bt-sg-linux-int-ecsinstance
    ]

    aws.subnet_id = "subnet-b63ac4fe" # Dev Private Subnet 1
    aws.associate_public_ip = false

    aws.tags = {
      "Name" => "#{APPNAME} (Vagrant: #{ENV['USERNAME']} / #{ENV['COMPUTERNAME']})",
      "Environment" => "Dev",
      "VagrantLaunched" => "True",
      "LaunchedBy" => "#{ENV['USERNAME']}"
    }

  end

  config.vm.post_up_message = <<-EOH

*** USAGE *** 
Enter the instance via:

    $ vagrant ssh

Your files have been synced to this folder inside the VM:
#{GUEST_SYNCED_FOLDER}

Vagrant port forwarding is not supported with AWS instances, so use the private IP of
the launched instance instead of localhost.

*** IMPORTANT ***
To enable live reload on Windows hosts, you must run the below command, without which
changes made on the host will not be continually synced into the VM.

    $ vagrant rsync-auto

EOH

end
