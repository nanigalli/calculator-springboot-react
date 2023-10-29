import "./ScrollableComponent.css";

const Results = ({ oldResults, onClick }) => {
    let orIdx = 1
    const orItems = oldResults.map(or => {
        const op = or.operator === "add"
        ? "+"
        : or.operator === "divide"
        ? "/"
        : or.operator === "subtract"
        ? "-"
        : "X"
        const item = <ul key={orIdx}>{or.leftNumber} {op} {or.rightNumber} = {or.result}</ul>
        orIdx = orIdx + 1
        return item
    })
    return (
        <div className="scrollableComponent" onClick={onClick}> 
            <ul>{orItems}</ul>
        </div>
    );
};

export default Results;