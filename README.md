# Playbook To Image

A [Source-to-Image (S2I)](https://docs.openshift.org/latest/architecture/core_concepts/builds_and_image_streams.html#source-build) builder image for packaging Ansible playbooks as a self-executing container.

## Usage

Prerequisites: an OpenShiftv3 cluster or s2i binary

In this workflow we build a new image with our playbook, setup secrets (private ssh key, for example) and create a job to run our playbook image.
 
1. Create a build. This will create a new image with your playbook sourcecode

        oc new-build docker.io/aweiteka/playbook2image~https://github.com/projectatomic/atomic-host-tests.git
2. Create a secret for our ssh private key

        oc secrets new-sshauth sshkey --ssh-privatekey=~/.ssh/id_rsa
3. Create a new job. Edit the sample-job.yaml file and run.

        oc create -f sample-job.yaml


### Environment Variable Options

**`PLAYBOOK_FILE`** (required)
Path to playbook file relative to project source. This is mounted in the container at **/opt/app-root/src/PLAYBOOK_FILE**.

**`INVENTORY_FILE`** (optional)
Path to inventory file relative to project source. This is mounted in the container at **/opt/app-root/src/INVENTORY_FILE**.

**`INVENTORY_URL`** (optional)
URL to inventory file. This is downloaded into the container as inventory file **/opt/app-root/src/inventory**.

**`DYNAMIC_SCRIPT_URL`** (optional)
URL to dynamic inventory script. This is downloaded into the container as **/opt/app-root/src/dynamic_inventory_script**. If the dynamic inventory script is python see **PYTHON_REQUIREMENTS**.

**`PYTHON_REQUIREMENTS`** (optional, default 'requirements.txt')
Path to python dependency requirements.txt file.

**`OPTS`** (optional)
List of options appended to ansible-playbook command. An example of commonly used options:

```
OPTS="-vvv --become --user cloud-user --private-key /var/secrets/id_rsa"
```

**`VAULT_PASS`** (optional)
ansible-vault passphrase for decrypting files. This is written to a file and used to decrypt ansible-vault files.

**`ANSIBLE_HOST_KEY_CHECKING=False`**
Disable host key checking. See http://docs.ansible.com/ansible/intro_getting_started.html#host-key-checking

## Notes and known issues:

* automatically replace ansible.cfg if custom file exists in base of playbook repo
* Remote dynamic inventory script only supports single file
* ssh key hard coded in script. Works with sample-job template.

## TODO use cases:

* I want to securely use an SSH private key and mount it with the correct permissions.
  * use secret mounted at /opt/app-root/src/
* I want to use a passphrase-protected SSH private key.
  * use a group-vars file protected by ansible-vault per [this post](https://dantehranian.wordpress.com/2015/08/17/encrypting-login-credentials-in-ansible-vault/)
  * see environment variable **VAULT_PASS**
* I want to securely use a system passphrase
  * need environment vars to support this.

## Contribute

**S2I project documentation**

[OpenShift docs](https://docs.openshift.org/latest/creating_images/s2i.html)
[Creating S2I images blog post](https://blog.openshift.com/create-s2i-builder-image/)

You'll need to download the s2i binary.

**Runining tests**

```
sudo make test
```

