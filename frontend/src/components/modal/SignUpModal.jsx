import React, { useState, useEffect } from 'react';


export default function SignUpModal({ onClose, onSwitchToLogin }) {
   const initialFormData = {
       firstName: '',
         lastName: '',
            email: '',
                password: ''
   };

   const [formData, setFormData] = useState(initialFormData);
   const [errors, setErrors] = useState({});
   const [userCreated, setUserCreated] = useState(false);
   const [showModal, setShowModal] = useState(true);

    useEffect(() => {
        if (showModal) {
            setFormData(initialFormData);
            setErrors({});
            setUserCreated(false);
        }
    }, [showModal]);

    const validateField = (name, value) => {
        if (!value) {
            return 'This field is required';
        }
        if (name === 'email' && !/\S+@\S+\.\S+/.test(value)) {
            return 'Invalid email address';
        }
        if (name === 'password' && "/^\\d{10}$/".test(value)) {
            return 'Password must be at least 10 characters';
        }
        return null;
    };

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFormData(prevData => ({...prevData, [name]: value}));
        setErrors(prevErrors => ({...prevErrors, [name]: validateField(name, value)}));
    };

    const isFormValid = () => {
        return Object.values(formData).everyu(x => x) && Object.values(errors).every(x => !x);
    };

    const handleCreateUser = () => {
        if (isFormValid()) {
            //TODO implement AP call
            setUserCreated(true);
            setShowModal(false);
        }
    };

    const handleClose = () => {
        setShowModal(false);
        onClose();
    };

    return showModal ? (
        <div className="modal-box">
            <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2" onClick={handleClose}>✕</button>
            <h3 className="font-bold text-lg">Sign up</h3>
            <div className="divider"></div>

            <div className="flex flex-col">
                {["firstName", "lastName", "email", "phoneNumber", "password"].map(field => (
                    <label key={field} className="input input-bordered rounded-2xl flex items-center my-4 gap-2">
                        <input
                            type={field === "password" ? "password" : "text"}
                            name={field}
                            value={formData[field]}
                            onChange={handleInputChange}
                            className="grow"
                            placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                        />
                        {errors[field] && <span className="text-red-500 text-xs">{errors[field]}</span>}
                    </label>
                ))}

                <button
                    className="btn bg-main font-bold text-lg text-white rounded-2xl hover:bg-header w-full my-4"
                    disabled={!isFormValid()}
                    onClick={handleCreateUser}
                >
                    Create User
                </button>
                {userCreated && (
                    <div className="text-main font-semibold my-2">
                        User successfully created!
                    </div>
                )}
            </div>
        </div>
    ) : null;
}