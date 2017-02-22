# Examples of playbook2image usage

This directory contains a few examples of how to use playbook2image.

- [build-openshift-ansible.yaml](build-openshift-ansible.yaml) is an example [template](https://docs.openshift.org/latest/dev_guide/templates.html) for a [build configuration](https://docs.openshift.org/latest/dev_guide/builds.html) and its supporting image streams whose purpose is to use `plabyook2image` to build a containerized version of [openshift-ansible](https://github.com/openshift/openshift-ansible) (the OpenShift installer code) inside OpenShift itself. You can use it like this:

        oc create -f build-openshift-ansible.yaml  # This imports the template
        oc new-app openshift-ansible               # This instantiates the template
        oc describe imagestream openshift-ansible  # To see details of the built image

- [build-template.yaml](build-template.yaml) is an [OpenShift template](https://docs.openshift.org/latest/dev_guide/templates.html) to build the `playbook2image` image itself inside OpenShift:

        oc create -f build-template.yaml  # This imports the template
        oc new-app playbook2image         # This instantiates the template

- [Dockerfile.example](Dockerfile.example) is an example `Dockerfile` to build an image based on `playbook2image` using `docker build`. See the Build section of the [README](../README.md) for details on the various options to build images.

- [sample-job.yaml](sample-job.yaml) is an example [Job spec](https://docs.openshift.org/latest/dev_guide/jobs.html) to run a specific playbook as a one time task. See the Run section of the [README](../README.md) for details on the various options to run images.
