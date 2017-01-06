# playbook2image
FROM openshift/base-centos7

MAINTAINER Aaron Weitekamp <aweiteka@redhat.com>

# TODO: Rename the builder environment variable to inform users about application you provide them
# ENV BUILDER_VERSION 1.0

LABEL io.k8s.description="Ansible playbook to image builder" \
      io.k8s.display-name="playbook2image" \
      #io.openshift.expose-services="8080:http" \
      io.openshift.tags="builder,ansible,playbook"

RUN yum install -y epel-release && yum clean all -y
RUN yum install -y ansible python-pip && yum clean all -y

# TODO (optional): Copy the builder files into /opt/app-root
#COPY ./<builder_folder>/ /opt/app-root/

# TODO: Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./.s2i/bin/ /usr/libexec/s2i

# TODO: Drop the root user and make the content of /opt/app-root owned by user 1001
RUN chown -R 1001:1001 /opt/app-root

# This default user is created in the openshift/base-centos7 image
USER 1001

CMD ["usage"]
