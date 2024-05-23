import React, { useState } from 'react';
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
import { sendAuthenticationRequest } from "@/tools/authentication";

import { useStore } from '@/tools/authentication';
import Head from "next/head";

const formSchema = z.object({
    email: z.string().email(),
    password: z.string().min(),
});

export default function LoginModalClient() {
    const [open, setOpen] = useState(false);
    const [showLogin, setShowLogin] = useState(true);

    const setUser = useStore((state) => state.setUser);

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
        //location.reload();
    }

    function onFailedLogin() {
        form.setError("password" , {
            type: "manual",
            message: "Incorrect password"
        });
        console.error("Login failed");
    }

    function handleShowSignup() {
        setShowLogin(false);
    }

    function handleShowLogin() {
        setShowLogin(true);
    }

    return (
        <>
            <Head>
                <title>Login</title>
            </Head>
            <Dialog open={open} onOpenChange={setOpen}>
                <DialogTrigger asChild>
                    <Button variant="ghost" size="icon">
                      <div width={32} height={32} className="flex items-center justify-center">
                        <Image src={userIcon} alt="User icon" width={32} height={32} />
                        </div>

                    </Button>
                </DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle id="DialogTitle">
                            {showLogin ? ("Login") : ("Sign up")}
                        </DialogTitle>
                        <DialogDescription>
                            {showLogin ? (
                                <span>Don't have an account? &nbsp;
                                    <a
                                        href="#"
                                        onClick={() => handleShowSignup()}
                                        className="text-blue-500 hover:text-blue-700 text-sm font focus:outline-none hover:underline"
                                    >
                                    Sign up
                                </a>
                            </span>
                            ) : (
                                <span> Already have an account? &nbsp;
                                    <a
                                        href="#"
                                        onClick={() => handleShowLogin()}
                                        className="text-blue-500 hover:text-blue-700 text-sm font focus:outline-none hover:underline"
                                    >
                                    Log in
                                </a>
                            </span>
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
        </>
    )
}

