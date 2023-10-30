import Wrapper from "./components/Wrapper";
import Screen from "./components/Screen";
import ButtonBox from "./components/ButtonBox";
import Button from "./components/Button";
import Results from "./components/Results"
import CollapseComponent from "./components/CollapseComponent"
import React, { useState } from "react";

const btnValues = [
  ["🔎", "C", "+-", "/"],
  [7, 8, 9, "x"],
  [4, 5, 6, "-"],
  [1, 2, 3, "+"],
  [0, ".", "="],
];

const operationTypes = {
  "+": "add",
  "-": "subtract",
  "/": "divide",
  "x": "multiply"
}

const toLocaleString = (num) =>
  String(num).replace(/(?<!\..*)(\d)(?=(?:\d{3})+(?:\.|$))/g, "$1 ");

const removeSpaces = (num) => num.toString().replace(/\s/g, "");

const App = () => {
  let [calc, setCalc] = useState({
    sign: "",
    num: 0,
    res: 0,
    visibleOldResult: false,
    pastResults: [],
    nextResultsPage: 0
  });

  const alertUnexpectedError = () => {
    alert("There was an unexpected error in the application, please contact support")
  }

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

  //The equalsClickHandler function calculates the result when the equals button (=) is pressed. 
  //The calculation is based on the current num and res value, as well as the sign selected.
  const equalsClickHandler = async () => {
    if (calc.sign && calc.num) {
      let result
      if (calc.num === "0" && calc.sign === "/") {
        result = "Can't divide by 0"
      } else {
        const operationName = operationTypes[calc.sign]
        let operationCall
        try {
          operationCall = await fetch(`/calculator/${operationName}?leftNumber=${removeSpaces(calc.res)}&rightNumber=${removeSpaces(calc.num)}`, {
            method: 'POST'
          })
        } catch (error) {
          console.log('There was an error', error);
          alertUnexpectedError()
        }

        if (operationCall?.ok) {
          const operationResult = await operationCall.json()
          console.log('operationResult = ', JSON.stringify(operationResult))

          result = toLocaleString(operationResult.result)
        } else {
          console.log(`Error executing operation -> HTTP Response Code: ${operationCall?.status}`)
          alertUnexpectedError()
          result = "Error"
        }
      }
      setCalc({
        ...calc,
        res: result,
        sign: "",
        num: 0,
      });
    }
  };

  const changeVisibleOldResult = async () => {
    if (!calc.visibleOldResult) {
      let resultsCall
      console.log('Results page: ', calc.nextResultsPage)
      try {
        resultsCall = await fetch(`/calculator/results?pageNumber=${calc.nextResultsPage}&pageSize=10`, {
          method: 'GET'
        })
      } catch (error) {
        console.log('There was an error', error);
        alertUnexpectedError()
      }

      if (resultsCall?.ok) {
        const resultsData = await resultsCall.json()
        console.log('Results: ', JSON.stringify(resultsData))

        setCalc({
          ...calc,
          visibleOldResult: !calc.visibleOldResult,
          pastResults: resultsData.results,
          nextResultsPage: calc.nextResultsPage +1
        });
      } else {
        console.log(`Error getting results -> HTTP Response Code: ${resultsCall?.status}`)
        alertUnexpectedError()
      }
    } else {
      setCalc({
        ...calc,
        visibleOldResult: !calc.visibleOldResult,
        nextResultsPage: 0
      });
    }
  }

  const moreResultsClickHandle = async () => {
      let resultsCall
      console.log('Results page: ', calc.nextResultsPage)
      try {
        resultsCall = await fetch(`/calculator/results?pageNumber=${calc.nextResultsPage}&pageSize=10`, {
          method: 'GET'
        })
      } catch (error) {
        console.log('There was an error', error);
        alertUnexpectedError()
      }

      if (resultsCall?.ok) {
        const resultsData = await resultsCall.json()
        console.log('Results: ', JSON.stringify(resultsData))

        setCalc({
          ...calc,
          pastResults: calc.pastResults.concat(resultsData.results),
          nextResultsPage: calc.nextResultsPage +1
        });
      } else {
        console.log(`Error getting results -> HTTP Response Code: ${resultsCall?.status}`)
        alertUnexpectedError()
      }
  }

  return (
    <Wrapper>
      <CollapseComponent isOpened={calc.visibleOldResult}>
        <Results onClick={changeVisibleOldResult} oldResults={calc.pastResults} />
        <Button key="MoreResults" className="moreResults" value="Load more results" onClick = {moreResultsClickHandle} />
      </CollapseComponent>
      <CollapseComponent isOpened={!calc.visibleOldResult}>
        <Screen onClick={changeVisibleOldResult} value={calc.num ? calc.num : calc.res} />
        <ButtonBox>
          {btnValues.flat().map((btn, i) => {
            return (
              <Button
                key={i}
                className={btn === "=" ? "equals" : ""}
                value={btn}
                onClick={
                  btn === "C"
                    ? resetClickHandler
                    : btn === "+-"
                      ? invertClickHandler
                      : btn === "="
                        ? equalsClickHandler
                        : btn === "/" || btn === "x" || btn === "-" || btn === "+"
                          ? signClickHandler
                          : btn === "."
                            ? commaClickHandler
                            : btn === "🔎"
                              ? changeVisibleOldResult
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
