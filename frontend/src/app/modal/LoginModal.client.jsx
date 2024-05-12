'use client';
import React, {useEffect} from 'react';

export  function LoginModalClient({ isOpen, onClose}) {

    useEffect(() => {
        const modal = document.getElementById('my_modal');
        modal.showModal();

        return () =>  modal.close();
    }, []);

    const handleLogin = (e) => {
        e.preventDefault();
        console.log('Login button clicked');
        onClose();
    }

    const closeModal = () => {
        document.getElementById('my_modal').close();
    }

    return isOpen ? (
        <div className="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center">
            <dialog id="my_modal" className="relative bg-white rounded-lg shadow-xl max-w-sm w-full p-6">
                <div className="modal-box">
                    <h3 className="text-lg font-semibold text-center mb-4">Log In or Create Account</h3>
                    <form onSubmit={handleLogin} className="space-y-6">
                        <div className="form-control">
                            <label className="block text-sm font-medium text-gray-700">
                                Username or Email
                                <input type="text" placeholder="Enter your username or email"
                                       className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" required/>
                            </label>
                        </div>
                        <div className="form-control">
                            <label className="block text-sm font-medium text-gray-700">
                                Password
                                <input type="password" placeholder="Enter your password"
                                       className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" required/>
                            </label>
                        </div>
                        <div className="flex items-center justify-between">
                            <button type="submit" className="btn bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                                Log in
                            </button>
                        </div>
                    </form>
                    <button onClick={onClose}
                            className="absolute top-0 right-0 mt-4 mr-4 text-gray-400 hover:text-gray-500 focus:outline-none focus:text-gray-500">
                        <span className="text-xl">&times;</span>
                    </button>
                </div>
            </dialog>
        </div>
    ) : null;
}