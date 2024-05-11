'use client';
import React, {useEffect} from 'react';

export  function LoginModalClient({ onClose}) {
    useEffect(() => {
        const modal = document.getElementById('my_modal');
        if (modal) {
            modal.showModal();
        }
        return () => {
            modal.close();
        }
    }, []);

    const handleLogin = () => {
        console.log('Login button clicked');
        onClose();
    }

    const closeModal = () => {
        document.getElementById('my_modal').close();
    }

    return (
        <div>
            <dialog id="my_modal" className="modal">
                <div className="modal-box">
                    <h3 className="font-bold text-lg mb-4">Opprett konto eller Log inn</h3>
                    <form>
                        <div className="form-control">
                            <label className="label">
                                <span className="label-text">Username or Email</span>
                                <input type="text" placeholder="Username or Email" className="input input-bordered w-full max-w-xs" />
                            </label>
                        </div>
                        <div className="form-control">
                            <label className="label">
                                <span className="label-text">Password</span>
                                <input type="password" placeholder="Password" className="input input-bordered w-full max-w-xs" />
                            </label>
                        </div>
                        <div className="form-control">
                            <button type="button" className="btn btn-primary" onClick={handleLogin}>Log in</button>
                        </div>
                    </form>
                    <button onClick={closeModal} className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕
                    </button>
                </div>
            </dialog>
        </div>
    );
}