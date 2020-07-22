#############
### build ###
#############

# base image
FROM node:12.2.0 as build

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# copy package.json and package-lock.json to container
COPY package.json package-lock.json ./

# install and cache app dependencies
RUN npm install

# add app
COPY . /app

# run tests - COMMENTED, later
#RUN npm run test

# generate build
RUN npm run build

############
### prod ###
############

# inspired by https://torstenwalter.de/openshift/nginx/2017/08/04/nginx-on-openshift.html

# base image
FROM nginx:1.16.0-alpine

# support running as arbitrary user which belogs to the root group
RUN chmod g+rwx /var/cache/nginx /var/run /var/log/nginx

# expose port 8081
EXPOSE 8081

# comment user directive as master process is run as user in OpenShift anyhow
RUN sed -i.bak 's/^user/#user/' /etc/nginx/nginx.conf

# copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# copy artifact build from the 'build environment'
COPY --from=build /app/build /usr/share/nginx/html

# run nginx
CMD ["nginx", "-g", "daemon off;"]
