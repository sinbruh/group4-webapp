import React, {useEffect} from 'react';
import { useState } from 'react';
import {asyncApiRequest} from "@/tools/request";

import { format } from 'date-fns';
import { useForm, Controller } from 'react-hook-form';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { getCookie } from '@/tools/cookies';

const formSchema = z.object({
    firstName: z.string(),
    lastName: z.string(),
    email: z.string().email(),
    dateOfBirth: z.date().nullable(),
    phoneNumber: z.string(),
});

const Email = getCookie('current_email');

const years = Array.from({ length: 100 }, (_, i) => new Date().getFullYear() - i);
const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

const getDaysInMonth = (year, month) => {
    return new Date(year, month + 1, 0).getDate();
};

export default function AccountSettings ({ userDetails, setOpen }) {
    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            firstName: userDetails.firstName,
            lastName: userDetails.lastName,
            email: userDetails.email,
            dateOfBirth: userDetails.dateOfBirth,
            phoneNumber: userDetails.phoneNumber.toString(),
            password: ''
        }
    });

    const [newPassword, setNewPassword] = useState('');
    const requestBody = {password: newPassword}
    const [isEditing, setIsEditing] = useState(false);
    const [editableDetails, setEditableDetails] = useState({});

    const handlePasswordChange = async (e) => {
        try {
            const response = await asyncApiRequest("PUT", `/api/users/${Email}/password`, requestBody);
            if (response.ok) {
                alert("Password changed successfully");
            } else {
                console.error("Error changing password: ", response);
            }
        } catch (error) {
            console.error("Error changing password: ", error);
            console.error("Response body: ", error.response.body);
        }
    };

    const handleEditClick = () => {
        if (isEditing) {
            setEditableDetails(userDetails);
        }
        setIsEditing(!isEditing);
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditableDetails((prevDetails) => ({
            ...prevDetails,
            [name]: value,
        }));
    }

    if (!userDetails) {
        return <p>Loading...</p>;
    }
    const onSubmit = async (values)  => {
        console.log("onSubmit", values);
        values.dateOfBirth = format(values.dateOfBirth, 'T');

        try {
            const response = await asyncApiRequest("PUT", `/api/users/${Email}`, values);

            if (response.ok) {
                setIsEditing(false);
            } else {
                console.error("Error updating user details: ", response);
            }
        } catch (error) {
            console.error("Error updating user details: ", error);
        }
    };

    return (
        <div className="max-w-7xl mx-auto p-6 bg-white shadow-md rounded-md">
            <Form {...form} className="space-y-6">
                <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                    <FormField
                        control={form.control}
                        name="firstName"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel className="font-semibold text-lg text-gray-700">First Name</FormLabel>
                                <FormControl>
                                    <Input {...field} className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                                </FormControl>
                                <FormMessage className="text-red-500">{form.formState.errors.firstName?.message}</FormMessage>
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="lastName"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel className="font-semibold text-lg text-gray-700">Last Name</FormLabel>
                                <FormControl>
                                    <Input {...field} className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                                </FormControl>
                                <FormMessage className="text-red-500">{form.formState.errors.lastName?.message}</FormMessage>
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="dateOfBirth"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel className="font-semibold text-lg text-gray-700">Date of Birth</FormLabel>
                                <FormControl>
                                    <Controller
                                        control={form.control}
                                        name="dateOfBirth"
                                        render={({ field }) => {
                                            const date = field.value ? new Date(field.value) : new Date();
                                            const [year, setYear] = useState(date.getFullYear());
                                            const [month, setMonth] = useState(date.getMonth());
                                            const [day, setDay] = useState(date.getDate());
                                            const days = Array.from({ length: getDaysInMonth(year, month) }, (_, i) => i + 1);

                                            useEffect(() => {
                                                const newDate = new Date(year, month, day);
                                                if (newDate.getMonth() === month && newDate.getDate() === day) {
                                                    field.onChange(newDate);
                                                } else {
                                                    setDay(1);
                                                    field.onChange(new Date(year, month, 1));
                                                }
                                            }, [year, month, day]);

                                            return (
                                                <div className="flex space-x-4">
                                                    <Select
                                                        value={year.toString()}
                                                        onValueChange={(value) => setYear(parseInt(value, 10))}
                                                        className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                    >
                                                        <SelectTrigger className="w-[100px]">
                                                            <SelectValue>{year}</SelectValue>
                                                        </SelectTrigger>
                                                        <SelectContent>
                                                            <SelectGroup>
                                                                {years.map(year => (
                                                                    <SelectItem key={year} value={year.toString()}>{year}</SelectItem>
                                                                ))}
                                                            </SelectGroup>
                                                        </SelectContent>
                                                    </Select>
                                                    <Select
                                                        value={months[month]}
                                                        onValueChange={(value) => setMonth(months.indexOf(value))}
                                                        className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                    >
                                                        <SelectTrigger className="w-[100px]">
                                                            <SelectValue>{months[month]}</SelectValue>
                                                        </SelectTrigger>
                                                        <SelectContent>
                                                            <SelectGroup>
                                                                {months.map((month, index) => (
                                                                    <SelectItem key={index} value={month}>{month}</SelectItem>
                                                                ))}
                                                            </SelectGroup>
                                                        </SelectContent>
                                                    </Select>
                                                    <Select
                                                        value={day.toString()}
                                                        onValueChange={(value) => setDay(parseInt(value, 10))}
                                                        className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                    >
                                                        <SelectTrigger className="w-[100px]">
                                                            <SelectValue>{day}</SelectValue>
                                                        </SelectTrigger>
                                                        <SelectContent>
                                                            <SelectGroup>
                                                                {days.map(day => (
                                                                    <SelectItem key={day} value={day.toString()}>{day}</SelectItem>
                                                                ))}
                                                            </SelectGroup>
                                                        </SelectContent>
                                                    </Select>
                                                </div>
                                            );
                                        }}
                                    />
                                </FormControl>
                                <FormMessage className="text-red-500">{form.formState.errors.dateOfBirth?.message}</FormMessage>
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="email"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel className="font-semibold text-lg text-gray-700">Email</FormLabel>
                                <FormControl>
                                    <Input {...field} className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                                </FormControl>
                                <FormMessage className="text-red-500">{form.formState.errors.email?.message}</FormMessage>
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="phoneNumber"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel className="font-semibold text-lg text-gray-700">Phone Number</FormLabel>
                                <FormControl>
                                    <Input {...field} className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                                </FormControl>
                                <FormMessage className="text-red-500">{form.formState.errors.phoneNumber?.message}</FormMessage>
                            </FormItem>
                        )}
                    />
                    <FormItem className="flex flex-col space-y-2">
                        <FormLabel className="font-semibold text-lg text-gray-700">New Password</FormLabel>
                        <FormControl>
                            <Input type="password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500" />
                        </FormControl>
                    </FormItem>
                    <Button type="button" onClick={handlePasswordChange} className="py-2 px-4 bg-blue-500 text-white rounded hover:bg-blue-600">Change Password</Button>

                    <Button type="submit" className="py-2 px-4 bg-blue-500 text-white rounded mt-4 hover:bg-blue-600">Submit</Button>
                </form>
            </Form>
        </div>
    );
}