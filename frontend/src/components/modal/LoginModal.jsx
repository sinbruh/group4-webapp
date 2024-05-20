import React, { useState, useEffect } from 'react';
import {usePathname} from 'next/navigation';
import {isUser} from "@/tools/authentication";
import SignupModal from './SignUpModal.jsx';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { Button } from "@/components/ui/button"
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog"
import userIcon from "@/img/icons/person.svg";
import Image from 'next/image';
import {sendAuthenticationRequest} from "@/tools/authentication";

import { useStore } from '@/tools/authentication';

const formSchema = z.object({
    email: z.string().email(),
    password: z.string().min(),
});

export default function LoginModalClient() {
    const [open, setOpen] = useState(false);
    const [showLogin, setShowLogin] = useState(true);

    const pathname = usePathname();

    const setUser = useStore((state) => state.setUser);

    useEffect(() => {
        if (pathname === '/profile' && !isUser()) {
            setOpen(true);
        }
    }, [pathname]);

    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            email: '',
            password: ''
        }
    });


    async function onSubmit(values) {
            sendAuthenticationRequest(values.email, values.password, onSuccessfulLogin, onFailedLogin);
    }

    function onSuccessfulLogin(user) {
        setUser(user)
        console.log(user.email + " has logged in");

        setOpen(false);
        location.reload();
    }

    function onFailedLogin() {
        console.error('Error:', error);
        console.error('Login failed');
    }

    function handleShowSignup() {
        setShowLogin(false);
    }

    function handleShowLogin() {
        setShowLogin(true);
    }

    return (
        <Dialog open={open} onOpenChange={setOpen}>
            <DialogTrigger asChild>
                <Button variant="ghost" size="icon">
                    <Image src={userIcon} alt="User icon" width={32} height={32} />
                </Button>
            </DialogTrigger>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle id="DialogTitle">
                        {showLogin ? ("Login") : ("Sign up")}
                    </DialogTitle>
                    <DialogDescription>
                        {showLogin ? (
                            <div>
                                Don't have an account? &nbsp;
                                <a
                                    href="#"
                                    onClick={() => handleShowSignup()}
                                    className="text-blue-500 hover:text-blue-700 text-sm font focus:outline-none hover:underline"
                                >
                                    Sign up
                                </a>
                            </div>
                        ) : (
                            <div>
                                Already have an account? &nbsp;
                                <a
                                    href="#"
                                    onClick={() => handleShowLogin()}
                                    className="text-blue-500 hover:text-blue-700 text-sm font focus:outline-none hover:underline"
                                >
                                    Log in
                                </a>
                            </div>
                        )}
                    </DialogDescription>
                </DialogHeader>
                {showLogin ? (
                    <Form {...form}>
                        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                            <FormField
                                control={form.control}
                                name="email"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Email</FormLabel>
                                        <FormControl>
                                            <Input {...field} />
                                        </FormControl>
                                        <FormMessage>{form.formState.errors.email?.message}</FormMessage>
                                    </FormItem>
                                )}
                            />
                            <FormField
                                control={form.control}
                                name="password"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Password</FormLabel>
                                        <FormControl>
                                            <Input type="password" {...field} />
                                        </FormControl>
                                        <FormMessage>{form.formState.errors.password?.message}</FormMessage>
                                    </FormItem>
                                )}
                            />
                            <Button type="submit">Log in</Button>
                        </form>
                    </Form>
                ) : (
                    <SignupModal setOpen={setOpen} />
                )}
            </DialogContent>
        </Dialog>

    )
}

// export function LoginModalClient({ isOpen, onClose }) {
//     const [formData, setFormData] = useState({ 'email': '', 'password': '' });
//     const [errors, setErrors] = useState(null);
//     const [showLogin, setShowLogin] = useState(true);
//
//     useEffect(() => {
//         const modal = document.getElementById('my_modal');
//         modal.showModal();
//
//         return () => modal.close();
//     }, []);
//
//     const handleInputChange = (e) => {
//         const { name, value } = e.target;
//         setFormData(prev => ({ ...prev, [name]: value }));
//         if (errors && errors[name]) {
//             setErrors(prev => ({ ...prev, [name]: null }));
//         }
//     };
//
//     const validateLoginForm = () => {
//         const newErrors = {};
//         if (!formData.email.trim()) newErrors.email = 'Field is required';
//         if (!formData.password.trim()) newErrors.password = 'Field is required';
//         setErrors(newErrors);
//         return Object.keys(newErrors).length === 0;
//     }
//
//     const handleLogin = (e) => {
//         e.preventDefault();
//         if (validateLoginForm()) {
//             console.log('Login attempt with:', formData);
//
//             fetch('http://localhost:8080/api/authorize', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json' },
//                 body: JSON.stringify(formData)
//             })
//                 .then(response => {
//                     if (response.ok) {
//                         console.log('Login successful');
//                         onClose();
//                     } else {
//                         console.log('Login failed');
//                         setErrors({ username: 'Invalid credentials', password: 'Invalid credentials' });
//                     }
//                 })
//                 .catch(error => {
//                     console.error('Error:', error);
//                 });
//
//         }
//     }
//
//     const handleSignup = (e) => {
//         setShowLogin(false);
//     }
//
//     const closeModal = () => {
//         document.getElementById('my_modal').close();
//     }
//
//     return isOpen ? (
//         <div className="fixed inset-0 bg-gray-500 bg-opacity-50 flex justify-center items-center">
//             <dialog id="my_modal" className="relative bg-white rounded-lg shadow-xl max-w-sm w-full p-6">
//                 {showLogin ? (
//                     <form onSubmit={handleLogin} className="space-y-6">
//                         <h3 className="text-lg font-semibold text-center mb-4">Log In</h3>
//                         <div className="form-control">
//                             <label className="block text-sm font-medium text-gray-700">
//                                 Username or Email
//                                 <input
//                                     type="text"
//                                     name="email"
//                                     placeholder="Enter your username or email"
//                                     value={formData.username}
//                                     onChange={handleInputChange}
//                                     className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" autoComplete="new-password"
//                                     required
//                                 />
//                             </label>
//                             {errors && errors.username && <p className="text-red-500">{errors.username}</p>}
//                         </div>
//                         <div className="form-control">
//                             <label className="block text-sm font-medium text-gray-700">
//                                 Password
//                                 <input
//                                     type="password"
//                                     name="password"
//                                     placeholder="Enter your password"
//                                     value={formData.password}
//                                     onChange={handleInputChange}
//                                     className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
//                                     required
//                                 />
//                             </label>
//                             {errors && errors.password && <p className="text-red-500">{errors.password}</p>}
//                         </div>
//                         <div className="flex flex-col items-center space-y-5">
//                             <button
//                                 type="submit"
//                                 className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
//                             >
//                                 Log in
//                             </button>
//                             <p className="text-center">
//                                 Don't have an account? &nbsp;
//                                 <a
//                                     href="#"
//                                     onClick={handleSignup}
//                                     className="text-blue-500 hover:text-blue-700 text-sm font focus:outline-none hover:underline"
//                                 >
//                                     Sign up
//                                 </a>
//                             </p>
//                         </div>
//                     </form>
//                 ) : (
//                     <SignupModal onClose={onClose} />
//                 )}
//                 <button onClick={onClose}
//                     className="absolute top-0 right-0 mt-4 mr-4 text-gray-400 hover:text-gray-500 focus:outline-none focus:text-gray-500">
//                     <span className="text-xl">&times;</span>
//                 </button>
//             </dialog>
//         </div>
//     ) : null;
// }
//
