# Stage 0, "build-stage", based on Node.js, to build and compile the frontend
FROM node:12.19.0 as build-stage
WORKDIR /app
COPY package*.json /app/
RUN npm install
COPY ./ /app/
ARG configuration=production
RUN npm run build -- --output-path=./dist/out --configuration $configuration

# Stage 1, based on Nginx, to have only the compiled app, ready for production with Nginx
FROM nginx:1.15
#Copy ci-dashboard-dist
ARG project=my-store
RUN mkdir -p /usr/share/nginx/html/$project
COPY --from=build-stage /app/dist/out/ /usr/share/nginx/html/$project
#Copy default nginx configuration
COPY ./conf/nginx-custom.conf /etc/nginx/conf.d/default.conf
COPY ./conf/nginx.crt /etc/ssl/nginx.crt
COPY ./conf/nginx.key /etc/ssl/nginx.key
