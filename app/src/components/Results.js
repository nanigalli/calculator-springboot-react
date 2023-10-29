import "./Results.css";

const operationTypes = {
    "add": "+",
    "subtract": "-",
    "divide": "/",
    "multiply": "x"
}

const Results = ({ oldResults, onClick }) => {
    let orIdx = 1
    const orItems = oldResults.map(or => {
        const op = operationTypes[or.operator]
        const item = <ul key={orIdx}>{or.leftNumber} {op} {or.rightNumber} = {or.result}</ul>
        orIdx = orIdx + 1
        return item
    })
    return (
        <div className="results" onClick={onClick}>
            <ul>{orItems}</ul>
        </div>
    );
};

export default Results;