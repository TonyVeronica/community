import "../style/components/Header.css";

function Header({ showBackButton, showCircleButton, nav }) {
    return (
        <div className="Header">
            <div className="Header-button-box">
                {showBackButton && (
                    <button className="Back-button" onClick={() => nav(-1)}>&lt;</button>
                )}
            </div>
            <div className="Header-title">아무 말 대잔치</div>
            <div className="Header-button-box">
                {showCircleButton && (
                    <button className="Circle-button"></button>
                )}
            </div>
        </div>
    );
}

export default Header;
