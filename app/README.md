# App

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

## Docker

### Create local image
`docker image build -t calculator-app:<<VERSION>> .`

### Create network
`docker network create calculator-net`

### Run docker compose (background)
1. Update imagen version in docker-compose.yml
2. Run command:
`docker-compose up -d`

### Stop docker compose
`docker-compose down`

### Check logs
1. Get container id:
`docker ps`
2. Logs:
`docker logs <<CONTAINER ID>>`

### Remove container
`docker rm <<CONTAINER ID>>`

