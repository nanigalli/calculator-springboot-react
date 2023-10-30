# Calculator
A calculator project built with Spring boot and React.

## Features
- Add, subtract, multiply, divide
- Support decimal values
- Clean screen
- Format larger numbers
- Display old results

## Usage example
### Mathematical operations
### Show executed operation
**Option 1**
![example](https://github.com/nanigalli/calculator-springboot-react/blob/first-release/app/public/logo192.png?raw=true)

**Option 2**

## Execution

### Requirements
- Java 17
- NodeJs 16.X.X

### Binaries
1. Go to release folder:
```
   cd release
```
2. Unzip last version:
```
   unzip calculator-springboot-react.zip
```
3. In separate terminals
- Run backend engine:
```
   java -jar engine-{lastVersion}.jar
```
- Run app:
```
   npm install http-server 
   npx http-server build -p 3000 -P "http://localhost:8080"
```
4. Visit: http://localhost:3000/

### Manual 
Compilation, build and execution
#### Backend
1. Go to engine folder:
```
   cd engine
```
2. Run backend engine:
```
   ./gradlew clean build bootRun
```

#### Frontend
1. Go to app folder:
```
   cd app
```
2. Install dependencies:
```
   npm install --force
```
3. Run app:
```
   npm start
```
4. Visit: http://localhost:3000/

## Data source
Database: H2 (file-based storage)

Settings: check /engine/src/main/resources/application.properties

Access the H2 Console: Run backend engine and visit http://localhost:8080/h2-console

Default location: /tmp/gallidb
