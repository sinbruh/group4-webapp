'use client';
import React, {useEffect, useState} from 'react';

export  function LoginModalClient({ isOpen, onClose}) {
    const [formData, setFormData] = React.useState({'username': '', 'password': ''});
    const [errors, setErrors] = React.useState(null);

    useEffect(() => {
        const modal = document.getElementById('my_modal');
        modal.showModal();

        return () =>  modal.close();
    }, []);

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setFormData(prev => ({...prev, [name]: value}));
        if (errors[name]) {
            setErrors(prev => ({...prev, [name]: null}));
        }
    };

    const validateLoginForm = () => {
        const newErrors = {};
        if (!formData.username.trim()) newErrors.username = 'Field is required';
        if (!formData.username.trim()) newErrors.username = 'Field is required';

    }

    const handleLogin = (e) => {
        e.preventDefault();
        if (!validateLoginForm()) {
            console.log('Login attempt with:', formData);
            onClose();
        }
    }

    const closeModal = () => {
        document.getElementById('my_modal').close();
    }

    return isOpen ? (
        <div className="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center">
            <dialog id="my_modal" className="relative bg-white rounded-lg shadow-xl max-w-sm w-full p-6">
                <form onSubmit={handleLogin} className="space-y-6">
                    <h3 className="text-lg font-semibold text-center mb-4">Log In or Create Account</h3>
                    <div className="form-control">
                        <label className="block text-sm font-medium text-gray-700">
                            Username or Email
                            <input
                                type="text"
                                name="username"
                                placeholder="Enter your username or email"
                                value={formData.username}
                                onChange={handleInputChange}
                                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"autoComplete="new-password"
                                required
                            />
                        </label>
                    </div>
                    <div className="form-control">
                        <label className="block text-sm font-medium text-gray-700">
                            Password
                            <input
                                type="password"
                                name="password"
                                placeholder="Enter your password"
                                value={formData.password}
                                onChange={handleInputChange}
                                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                                required
                            />
                        </label>
                    </div>
                    <div className="flex items-center justify-between">
                        <button
                            type="submit"
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                        >
                            Log in
                        </button>
                    </div>
                </form>
                <button onClick={onClose}
                        className="absolute top-0 right-0 mt-4 mr-4 text-gray-400 hover:text-gray-500 focus:outline-none focus:text-gray-500">
                    <span className="text-xl">&times;</span>
                </button>
            </dialog>
        </div>
    ) : null;
}