import { Collapse } from 'react-collapse';
import "./CollapseComponent.css";

const CollapseComponent = ({ children, isOpened }) => {
    return (
        <Collapse className="collapseComponent" isOpened={isOpened}>{children}</Collapse>
    );
};

export default CollapseComponent;