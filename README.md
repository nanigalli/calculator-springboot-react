# Calculator
A calculator project built with Spring boot and React.

## Features
- Add, subtract, multiply, divide
- Support decimal values
- Clean screen
- Format larger numbers
- Display old results

## Data source
Database: H2 (file-based storage)

Access the H2 Console: Run backend engine and visit http://localhost:8080/h2-console

Default location: /tmp/gallidb

Alternatively, the database file location can be set by specifying the `DATASOURCE_DIR` environment variable or java parameter:
- `java -DDATASOURCE_DIR="{DATASOURCE_DIR}" engine-{lastVersion}.jar`
- `export DATASOURCE_DIR="{DATASOURCE_DIR}" ; java engine-{lastVersion}.jar`

## Demo

### Mathematical operations

You can operate with numbers up to 16 digits.

![MathOperations](https://github.com/nanigalli/calculator-springboot-react/blob/main/docs/calculator.gif?raw=true)


### Historical calculations
**Option 1**

You can click on the calculator screen to view past calculations, and then click the screen again to return to the calculator.

![AllResultsClickScreen](https://github.com/nanigalli/calculator-springboot-react/blob/main/docs/allResultsScreen.gif?raw=true)


**Option 2**

You can also click on the "🔎" button to view past calculations, and then click the screen again to return to the calculator.

![AllResultsClickButton](https://github.com/nanigalli/calculator-springboot-react/blob/main/docs/allResultsButton.gif?raw=true)


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
   unzip calculator-springboot-react-{lastVersion}.zip
```
3. Go to calculator-springboot-react-{lastVersion} folder:
```
   cd calculator-springboot-react-{lastVersion}
```
4. In separate terminals
- Run backend engine:
```
   java -jar engine-{lastVersion}.jar
   
   or:
   
   java -DDATASOURCE_DIR="{DATASOURCE_DIR}" engine-{lastVersion}.jar
```
- Run app:
```
   npm install http-server 
   npx http-server build -p 3000 -P "http://localhost:8080"
```
5. Visit: http://localhost:3000/

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

## Tech debts
- Find a new component to replace "Textfit" from "react-textfit in react App. It is not compatible with React 18
