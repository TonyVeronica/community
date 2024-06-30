import "../style/components/InfoInput.css";

function InfoInput(props) {
    return (
        <div className="InputBox">
            <div className="InputBoxTitle">{props.title}</div>
            <input
                className="InputBoxContent"
                type={props.type}
                placeholder={props.placeholder}
                value={props.value}
                onChange={props.onChange}
            />
            <div className={`HelperMsg ${props.helperStyle}`}>{props.helperMsg}</div>
        </div>
    );
}

export default InfoInput;
