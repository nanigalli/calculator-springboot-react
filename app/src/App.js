import Wrapper from "./components/Wrapper";
import Screen from "./components/Screen";
import ButtonBox from "./components/ButtonBox";
import Button from "./components/Button";
import Results from "./components/Results"
import CollapseComponent from "./components/CollapseComponent"
import React, { useState } from "react";

const btnValues = [
  ["C", "+-", "/"],
  [7, 8, 9, "X"],
  [4, 5, 6, "-"],
  [1, 2, 3, "+"],
  [0, ".", "="],
];

const toLocaleString = (num) =>
  String(num).replace(/(?<!\..*)(\d)(?=(?:\d{3})+(?:\.|$))/g, "$1 ");

const removeSpaces = (num) => num.toString().replace(/\s/g, "");

const App = () => {
  let [calc, setCalc] = useState({
    sign: "",
    num: 0,
    res: 0,
    visibleOldResult: false,
    pastResults: []
  });

  //The numClickHandler function gets triggered only if any of the number buttons (0–9) are pressed. Then it gets the value of the Button and adds that to the current num value.
  //It will also make sure that:
  // + no whole numbers start with zero
  // + there are no multiple zeros before the comma
  // + the format will be “0.” if “.” is pressed first
  // + numbers are entered up to 16 integers long

  const numClickHandler = (e) => {
    e.preventDefault();
    const value = e.target.innerHTML;

    if (removeSpaces(calc.num).length < 16) {
      setCalc({
        ...calc,
        num:
          calc.num === 0 && value === "0"
            ? "0"
            : removeSpaces(calc.num) % 1 === 0
            ? toLocaleString(Number(removeSpaces(calc.num + value)))
            : toLocaleString(calc.num + value),
        res: !calc.sign ? 0 : calc.res,
      });
    }
  };

  // The commaClickHandler function gets fired only if the decimal point (.) is pressed. 
  //It adds the decimal point to the current num value, making it a decimal number.
  //It will also make sure that no multiple decimal points are possible.
  const commaClickHandler = (e) => {
    e.preventDefault();
    const value = e.target.innerHTML;

    setCalc({
      ...calc,
      num: !calc.num.toString().includes(".") ? calc.num + value : calc.num,
    });
  };

  //The invertClickHandler function first checks if there’s any entered value (num) 
  // or calculated value (res) and then inverts them by multiplying with -1
  const invertClickHandler = () => {
    setCalc({
      ...calc,
      num: calc.num ? toLocaleString(removeSpaces(calc.num) * -1) : 0,
      res: calc.res ? toLocaleString(removeSpaces(calc.res) * -1) : 0,
      sign: "",
    });
  };

  //The resetClickHandler function defaults all the initial values of calc, returning the calc state as it was when 
  // the Calculator app was first rendered
  const resetClickHandler = () => {
    setCalc({
      ...calc,
      sign: "",
      num: 0,
      res: 0,
    });
  };

  //The signClickHandler function gets fired when the user press either +, –, * or /.
  //The particular value is then set as a current sign value in the calc object.
  const signClickHandler = (e) => {
    e.preventDefault();
    const value = e.target.innerHTML;
  
    setCalc({
      ...calc,
      sign: value,
      res: !calc.res && calc.num ? calc.num : calc.res,
      num: 0,
    });
  };

  //The equalsClickHandler function calculates the result when the equals button (=) is pressed. The calculation is based on the current num and res value, as well as the sign selected (see the math function).
  //The returned value is then set as the new res for the further calculations.
  //It will also make sure that:
  // + there’s no effect on repeated calls
  // + users can’t divide with 0
  const equalsClickHandler = async () => {
    if (calc.sign && calc.num) {
      const operationTypes = {
        "+": "add",
        "-": "subtract",
        "/": "divide",
        "X": "multiply"
      }
      const operationName = operationTypes[calc.sign]

      const operationCall = await fetch(`http://localhost:8080/calculator/${operationName}?leftNumber=${calc.res}&rightNumber=${calc.num}`, {
        method: 'POST'
      })

      const operationResult = await operationCall.json()

      console.log('operationResult = ', JSON.stringify(operationResult))

      const math = (a, b, sign) =>
        sign === "+"
          ? operationResult.result
          : sign === "-"
          ? a - b
          : sign === "X"
          ? a * b
          : a / b;

      setCalc({
        ...calc,
        res:
          calc.num === "0" && calc.sign === "/"
            ? "Can't divide with 0"
            : toLocaleString(
                math(
                  Number(removeSpaces(calc.res)),
                  Number(removeSpaces(calc.num)),
                  calc.sign
                )
              ),
        sign: "",
        num: 0,
      });
    }
  };

  const changeVisibleOldResult = async () => {
    if (!calc.visibleOldResult) {
    const resultsCall = await fetch(
      'http://localhost:8080/calculator/results',{
        method: 'GET'
      })  
      const resultsData = await resultsCall.json()
      console.log('Results: ', JSON.stringify(resultsData))

      setCalc({
        ...calc,
        visibleOldResult: !calc.visibleOldResult,
        pastResults: resultsData.results
      });
    } else {
      setCalc({
        ...calc,
        visibleOldResult: !calc.visibleOldResult
      });
    }
  }

  return (
    <Wrapper>
      <CollapseComponent isOpened={calc.visibleOldResult}>
        <Results onClick={changeVisibleOldResult} oldResults={calc.pastResults} />
      </CollapseComponent>
      <CollapseComponent isOpened={!calc.visibleOldResult}>
        <Screen onClick={changeVisibleOldResult} value={calc.num ? calc.num : calc.res} />
        <ButtonBox>
          {btnValues.flat().map((btn, i) => {
            return (
              <Button
                key={i}
                className={btn === "=" ? "equals" : btn === "C" ? "reset": ""}
                value={btn}
                onClick={
                  btn === "C"
                    ? resetClickHandler
                    : btn === "+-"
                    ? invertClickHandler
                    : btn === "="
                    ? equalsClickHandler
                    : btn === "/" || btn === "X" || btn === "-" || btn === "+"
                    ? signClickHandler
                    : btn === "."
                    ? commaClickHandler
                    : numClickHandler
                }
              />
            );
          })}
        </ButtonBox>
      </CollapseComponent>
    </Wrapper>
  );
}

export default App;
