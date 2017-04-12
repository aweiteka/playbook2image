# Examples of playbook2image usage

This directory contains a few examples of how to use playbook2image.

- [build-openshift-ansible.yaml](build-openshift-ansible.yaml) is an example [template](https://docs.openshift.org/latest/dev_guide/templates.html) for a [build configuration](https://docs.openshift.org/latest/dev_guide/builds.html) and its supporting image streams whose purpose is to use `plabyook2image` to build a containerized version of [openshift-ansible](https://github.com/openshift/openshift-ansible) (the OpenShift installer code) inside OpenShift itself. You can use it like this:

        oc create -f build-openshift-ansible.yaml  # This imports the template
        oc new-app openshift-ansible               # This instantiates the template
        oc describe imagestream openshift-ansible  # To see details of the built image

- [build-template.yaml](build-template.yaml) is an [OpenShift template](https://docs.openshift.org/latest/dev_guide/templates.html) to build the `playbook2image` image itself inside OpenShift:

        oc create -f build-template.yaml  # This imports the template
        oc new-app playbook2image         # This instantiates the template

- [Dockerfile.simple](Dockerfile.simple) is an example of a very simple `Dockerfile` to  build an image that packages a playbook `docker build` using `playbook2image` as a base.

- [Dockerfile.advanced](Dockerfile.advanced) is a more elaborate `Dockerfile` example that installs additional dependencies needed to run the packaged playbooks. See the Build section of the [README](../README.md) for details on the various options to build images.

- [sample-job.yaml](sample-job.yaml) is an example [Job spec](https://docs.openshift.org/latest/dev_guide/jobs.html) to run a playbook as a one-time Job in OpenShift/Kubernetes. The Job expects a [Secret](https://docs.openshift.org/latest/dev_guide/secrets.html) named `sshkey` with an attribute named `ssh-privatekey` that contains the ssh key to connect to hosts. See the Run section of the [README](../README.md) for details on the various options to run images.

The [openshift-ansible](https://github.com/openshift/openshift-ansible) repository (OpenShift's installation and configuration tooling) uses `playbook2image` as a base to create its container image. The repository contains some additional examples that illustrate how to build and use an image using `playbook2image`. See the [README_CONTAINER_IMAGE.md](https://github.com/openshift/openshift-ansible/blob/master/README_CONTAINER_IMAGE.md), the [Dockerfile](https://github.com/openshift/openshift-ansible/blob/master/Dockerfile) in the repo and some additional [openshift-ansible container usage examples](https://github.com/openshift/openshift-ansible/tree/master/examples).
