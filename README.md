# Calculator
A calculator project built with Spring boot and React.

## Features
- Add, subtract, multiply, divide
- Support decimal values
- Clean screen
- Format larger numbers
- Display old results

## Execution

### Requirements
- Java 17
- NodeJs 16.X.X

### Binaries
1. Go to release folder:
   ```cd release```
2. Unzip last version:
   ```unzip calculator-springboot-react-{lastVersion}.zip```
3. In separate terminals
   - Run backend engine:
      ```java -jar engine-{lastVersion}.jar```
   - Run app:
   ```npm start```
4. Visit: http://localhost:3000/

### Manual 
Compilation, build and execution
#### Backend
1. Go to engine folder:
```cd engine```
2. Run backend engine:
```./gradlew clean build bootRun```

#### Frontend
1. Go to app folder:
```cd app```
2. Install dependencies:
```npm install --force```
3. Run app:
```npm start```
4. Visit: http://localhost:3000/
