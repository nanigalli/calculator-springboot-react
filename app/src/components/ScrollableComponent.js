import "./ScrollableComponent.css";

const ScrollableComponent = ({ oldResults, onClick }) => {
    let orIdx = 1
    const orItems = oldResults.map(or => {
        const item = <ul key={orIdx}>{or}</ul>
        orIdx = orIdx + 1
        return item
    })
    return (
        <div className="scrollableComponent" onClick={onClick}> 
            <ul>{orItems}</ul>
        </div>
    );
};

export default ScrollableComponent;