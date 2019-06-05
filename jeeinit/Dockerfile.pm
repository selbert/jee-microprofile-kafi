FROM payara/server-full
#FROM payara/micro

COPY target/shop.war $DEPLOY_DIR