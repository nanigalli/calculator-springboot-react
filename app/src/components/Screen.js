import { Textfit } from "react-textfit";
import "./Screen.css";

const Screen = ({ value, onClick }) => {
  return (
    <Textfit className="screen" mode="single" max={70} onClick={onClick}>
      {value}
    </Textfit>
  );
};

export default Screen;