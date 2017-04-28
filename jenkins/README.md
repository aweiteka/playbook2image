# Deploying Jenkins to test playbook2image

1. Create project

        oc new-project p2i-ci

1. Using a browser, create a Github token at this URL: https://github.com/settings/tokens/new?scopes=repo,read:user,user:email
1. Copy the token and create a basic auth secret in OpenShift

        oc secrets new-basicauth github --username=GH_USERNAME --password=GH_TOKEN

1. Build a Jenkins master container image with the required plugins. We use OpenShift's source-to-image build to install the plugins listed in the jenkins/plugins.txt file.

        oc new-build openshift/jenkins~https://github.com/openshift/playbook2image \
        --context-dir='jenkins' --name='jenkins'
1. Deploy the jenkins server

        oc process openshift//jenkins-ephemeral \
        NAMESPACE=p2i-ci MEMORY_LIMIT=2Gi | oc create -f -

1. Create the OpenShift pipeline

        oc create -f jenkins/openshift/pipeline.yaml
