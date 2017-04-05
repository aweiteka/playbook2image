#!groovy

@Library('Utils')
import com.redhat.*

node {
    def id = null
    def utils = new Utils()

    stage('OpenShift -> Jenkins credentials') {
        openshift.withCluster() {
            def secret = openshift.selector( "secret/github" ).object()
            id = utils.createCredentialsFromOpenShift(secret, "github") 
        }
    }
    stage('Run Seed Job') {            
        build job: 'seed', parameters: [[$class: 'StringParameterValue', name: 'CRED_ID', value: id]]
    }
}
