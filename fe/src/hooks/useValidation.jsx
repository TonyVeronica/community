import { useState } from 'react';
import { setHelperMsgAndStyle } from '../util/validator';

const useValidation = (initialValue, validateFunction) => {
    const [value, setValue] = useState(initialValue);
    const [helperMsg, setHelperMsg] = useState("");
    const [helperStyle, setHelperStyle] = useState("error");
    const [isValid, setIsValid] = useState(false);

    const handleChange = (e) => {
        const value = e.target.value;
        setValue(value);
        const validation = validateFunction(value);
        setHelperMsgAndStyle(setHelperMsg, setHelperStyle, validation);
        setIsValid(validation.isValid);
    };

    return { value, helperMsg, helperStyle, isValid, handleChange };
};

export default useValidation;