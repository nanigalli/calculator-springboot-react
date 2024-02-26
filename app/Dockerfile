FROM node:18-alpine

WORKDIR /react-docker/

COPY public/ /react-docker/public
COPY src/ /react-docker/src
COPY package-docker.json /react-docker/package.json
COPY build/ /react-docker/build
COPY node_modules/ /react-docker/node_modules

#RUN npm install
RUN npm run build

CMD ["npm", "start"]