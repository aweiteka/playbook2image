# Playbook To Image

A [Source-to-Image (S2I)](https://docs.openshift.org/latest/architecture/core_concepts/builds_and_image_streams.html#source-build) builder image for packaging Ansible playbooks as a self-executing container.

## Usage

Prerequisites: an OpenShiftv3 cluster or [s2i binary](https://github.com/openshift/source-to-image/releases)

In this workflow we build a new image with our playbook, setup secrets (private ssh key, for example) and create a job to run our playbook image.

1. **Build**: Add your playbook to the image. This will create a new image with your playbook sourcecode
  * Using OpenShift:

          oc new-build docker.io/aweiteka/playbook2image~https://github.com/PLAYBOOK/REPO.git
  * Using docker:
    1. Using the [example Dockerfile](Dockerfile.example), create a Dockerfile in the playbook repository.
    1. Build the image

            docker build -t IMAGE_NAME . -f Dockerfile.example
  * Using s2i CLI tool:

          sudo s2i build https://github.com/PLAYBOOK/REPO.git docker.io/aweiteka/playbook2image NEW_PLAYBOOK_IMAGE_NAME
1. **Run**: as an [OpenShift Job](https://docs.openshift.org/latest/dev_guide/jobs.html) or with docker via command line
  * Using OpenShift:
    1. Create a secret for our ssh private key

            oc secrets new-sshauth sshkey --ssh-privatekey=~/.ssh/id_rsa
    1. Create a new job. Download the [sample-job.yaml](https://raw.githubusercontent.com/aweiteka/playbook2image/master/sample-job.yaml) file, edit and create the job.

            oc create -f sample-job.yaml
  * Using Docker (example command):

          sudo docker run \
               -v ~/.ssh/id_rsa:/opt/app-root/src/.ssh/id_rsa \
               -e OPTS="--become --user cloud-user" \
               -e PLAYBOOK_FILE=PATH_TO_PLAYBOOK \
               -e INVENTORY_URL=URL \
               IMAGE_FROM_BUILD_STEP

### Environment Variable Options

**`PLAYBOOK_FILE`** (required)

Relative path to playbook file relative to project source. This is mounted in the container at **/opt/app-root/src/PLAYBOOK_FILE**.

**`INVENTORY_FILE`** (optional)

Relative path to inventory file relative to project source. This is mounted in the container at **/opt/app-root/src/INVENTORY_FILE**.

**`INVENTORY_URL`** (optional)

URL to inventory file. This is downloaded into the container as inventory file **/opt/app-root/src/inventory**.

**`ALLOW_ANSIBLE_CONNECTION_LOCAL`** (optional)

If set to false all `ansible_connection=local` settings will be ignored.

**`DYNAMIC_SCRIPT_URL`** (optional)

URL to dynamic inventory script. This is downloaded into the container as **/opt/app-root/src/dynamic_inventory_script**. If the dynamic inventory script is python see **PYTHON_REQUIREMENTS**.

**`PYTHON_REQUIREMENTS`** (optional, default 'requirements.txt')

Relative path to python dependency requirements.txt file to support dynamic inventory script.

**`ANSIBLE_PRIVATE_KEY_FILE`** (optional, e.g. '/opt/app-root/src/.ssh/id_rsa/ssh-privatekey')

Container path to mounted private SSH key. For OpenShift this must match the secret volumeMount (see mountPath in [sample-job.yaml](sample-job.yaml)). For docker this must match the bindmount container path, e.g. `-v ~/.ssh/id_rsa:/opt/app-root/src/.ssh/id_rsa`.

**`OPTS`** (optional)

List of options appended to ansible-playbook command. An example of commonly used options:

```
OPTS="-vvv --become --user cloud-user"
```

**`VAULT_PASS`** (optional)

ansible-vault passphrase for decrypting files. This is written to a file and used to decrypt ansible-vault files.

**`ANSIBLE_HOST_KEY_CHECKING=False`**

Disable host key checking. See [documentation](http://docs.ansible.com/ansible/intro_getting_started.html#host-key-checking)

## Contribute

**S2I project documentation**

* [OpenShift docs](https://docs.openshift.org/latest/creating_images/s2i.html)

* [Creating S2I images blog post](https://blog.openshift.com/create-s2i-builder-image/)

To run tests you will need to [install the s2i binary](https://github.com/openshift/source-to-image#installation).

**Running tests**

```
sudo make test
```

