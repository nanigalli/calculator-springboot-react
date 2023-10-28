import {Collapse} from 'react-collapse';
import "./CollapseComponent.css";

//https://www.npmjs.com/package/react-collapse
const CollapseComponent = ({ children, isOpened}) => {
    return (
        <Collapse className="collapseComponent" isOpened={isOpened}>{children}</Collapse>
    );
};

export default CollapseComponent;