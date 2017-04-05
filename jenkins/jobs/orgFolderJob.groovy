#!groovy

organizationFolder('aweiteka') {
    description('This contains branch source jobs for GitHub')
    displayName('aweiteka')
  	orphanedItemStrategy {
        discardOldItems {
		    daysToKeep(0)
		    numToKeep(0)
        }
	}
	organizations {
		github {
            apiUri('https://api.github.com')
			repoOwner('aweiteka')
			scanCredentialsId("${CRED_ID}")
			pattern('playbook2image')
			checkoutCredentialsId("${CRED_ID}")
			buildOriginBranch(true)
			buildOriginBranchWithPR(true)
			buildOriginPRMerge(false)
			buildOriginPRHead(false)
			buildForkPRMerge(true)
			buildForkPRHead(false)
		}
    }
    triggers {
        periodic(10)
    }
}
