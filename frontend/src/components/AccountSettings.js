import React, { useEffect, useState } from "react";
import { asyncApiRequest } from "@/tools/request";
import { format } from "date-fns";
import { useForm, Controller } from "react-hook-form";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
  } from "@/components/ui/dialog"
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useRouter } from "next/navigation";
import { useStore } from "@/tools/authentication";

const formSchema = z.object({
  firstName: z.string(),
  lastName: z.string(),
  email: z.string().email(),
  dateOfBirth: z.date().nullable(),
  phoneNumber: z.string(),
});

const years = Array.from(
  { length: 100 },
  (_, i) => new Date().getFullYear() - i
);
const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const getDaysInMonth = (year, month) => {
  return new Date(year, month + 1, 0).getDate();
};

export default function AccountSettings({ userDetails, setOpen }) {
  const router = useRouter()
  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      firstName: userDetails.firstName,
      lastName: userDetails.lastName,
      email: userDetails.email,
      dateOfBirth: userDetails.dateOfBirth,
      phoneNumber: userDetails.phoneNumber.toString(),
      password: "",
    },
  });

  const [newPassword, setNewPassword] = useState("");
  const [newPasswordCheck, setNewPasswordCheck] = useState("");
  const requestBody = { password: newPassword };
  const [isEditing, setIsEditing] = useState(false);
  const [editableDetails, setEditableDetails] = useState({});
  const [passwordChangeStatus, setPasswordChangeStatus] = useState(null);
  const [deletePassword, setDeletePassword] = useState('');
  const [deletePasswordCheck, setDeletePasswordCheck] = useState('');
  const [deleteAccountStatus, setDeleteAccountStatus] = useState('');
  const user = useStore((state) => state.user);
  const logout = useStore((state) => state.logout);

  const handlePasswordChange = async (e) => {
    if (newPassword !== newPasswordCheck) {
        setPasswordChangeStatus('The passwords do not match.');
        return;
      }
    try {
      const response = await asyncApiRequest(
        "PUT",
        `/api/users/${Email}/password`,
        requestBody,true
      );
      setPasswordChangeStatus('Password changed successfully.');  
        
    } catch (error) {
      setPasswordChangeStatus('Failed to change password.', response);
      console.error("Error changing password: ", error);
      console.error("Response body: ", error.response.body);
    }
  };
  
  const deleteRentals = async () => {
    if (userDetails.rentals) {
    for (const rental of userDetails.rentals) {
      try {
        const response = await asyncApiRequest(
          "DELETE",
          `/api/rentals/${rental.id}`
        );
      } catch (error) {
        console.error(`Error deleting rental with id ${rental.id}: `, error);
      }
    }
    }
  };

  const deleteReceipts = async () => {
    if (userDetails.receipts) {
    for (const receipt of userDetails.receipts) {
      try {
        const response = await asyncApiRequest(
          "DELETE",
          `/api/receipts/${receipt.id}`
        );
      } catch (error) {
        console.error(`Error deleting receipt with id ${receipt.id}: `, error);
      }
    }
    }
  };

  const handleDeleteAccount = async () => {
    if (deletePassword !== deletePasswordCheck) {
      setDeleteAccountStatus('The passwords do not match.');
      return;
    }
  
    try {
      await deleteRentals();
      await deleteReceipts();  
  
      const response = await asyncApiRequest(
        "DELETE",
        `/api/users/${user.email}`,
        requestBody,true
      );
  
    } catch (error) {
      console.error("Error deleting account: ", error);
      setDeleteAccountStatus('Failed to delete account.');
    }
  };


  const handleEditClick = () => {
    if (isEditing) {
      setEditableDetails(userDetails);
    }
    setIsEditing(!isEditing);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    form.reset(userDetails);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditableDetails((prevDetails) => ({
      ...prevDetails,
      [name]: value,
    }));
  };

  if (!userDetails) {
    return <p>Loading...</p>;
  }

  const onSubmit = async (values) => {
    values.dateOfBirth = format(values.dateOfBirth, "T");

    try {
      const response = await asyncApiRequest(
        "PUT",
        `/api/users/${Email}`,
        values
      );

      setIsEditing(false);
      router.push('/');

    } catch (error) {
      console.error("Error updating user details: ", error);
      if (response) {
        console.error("Error updating user details: ", response);
      }
    }
  };

  return (
    <div className="mx-auto p-6  flex flex-col  overflow-auto">
      <div className={"flex-grow overflow-auto"}>
        
        <Form {...form} onSubmit={form.handleSubmit(onSubmit)} className="space-y-6" id="account-settings-form">
        <div className="flex justify-end space-x-2 mb-4">
          {!isEditing ? (
            <Button
              onClick={handleEditClick}
              className="py-1 px-3 bg-gray-800 text-white rounded hover:bg-gray-700"
            >
              Edit Profile
            </Button>
          ) : (
            <>
              <Button
                onClick={handleCancelEdit}
                className="py-1 px-3 bg-gray-500 text-white rounded hover:bg-gray-400"
              >
                Cancel
              </Button>
              <Button
                type="submit"
                form="account-settings-form"
                className="py-1 px-3 bg-green-600 text-white rounded hover:bg-green-500"
                onClick={(event) => {
                    event.preventDefault();
                    form.handleSubmit(onSubmit)();
                }}
            >
                Save
            </Button>
            </>
          )}
        </div>
         
            <FormField
              control={form.control}
              name="firstName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="font-semibold text-lg text-gray-700">
                    First Name
                  </FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      readOnly={!isEditing}
                      className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />
                  </FormControl>
                  <FormMessage className="text-red-500">
                    {form.formState.errors.firstName?.message}
                  </FormMessage>
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="lastName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="font-semibold text-lg text-gray-700">
                    Last Name
                  </FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      readOnly={!isEditing}
                      className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />
                  </FormControl>
                  <FormMessage className="text-red-500">
                    {form.formState.errors.lastName?.message}
                  </FormMessage>
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="dateOfBirth"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="font-semibold text-lg text-gray-700">
                    Date of Birth
                  </FormLabel>
                  <FormControl>
                    <Controller
                      control={form.control}
                      name="dateOfBirth"
                      render={({ field }) => {
                        const date = field.value
                          ? new Date(field.value)
                          : new Date();
                        const [year, setYear] = useState(date.getFullYear());
                        const [month, setMonth] = useState(date.getMonth());
                        const [day, setDay] = useState(date.getDate());
                        const days = Array.from(
                          { length: getDaysInMonth(year, month) },
                          (_, i) => i + 1
                        );

                        useEffect(() => {
                          const newDate = new Date(year, month, day);
                          if (
                            newDate.getMonth() === month &&
                            newDate.getDate() === day
                          ) {
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
                              onValueChange={(value) =>
                                setYear(parseInt(value, 10))
                              }
                              className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                            >
                              <SelectTrigger className="w-[100px]" aria-label="Select Year">
                                <SelectValue>{year}</SelectValue>
                              </SelectTrigger>
                              <SelectContent>
                                <SelectGroup>
                                  {years.map((year) => (
                                    <SelectItem
                                      key={year}
                                      value={year.toString()}
                                      alt={"Year:" + year}
                                    >
                                      {year}
                                    </SelectItem>
                                  ))}
                                </SelectGroup>
                              </SelectContent>
                            </Select>
                            <Select
                              value={months[month]}
                              onValueChange={(value) =>
                                setMonth(months.indexOf(value))
                              }
                              className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                            >
                              <SelectTrigger className="w-[100px]" aria-label="Select Month">
                                <SelectValue>{months[month]}</SelectValue>
                              </SelectTrigger>
                              <SelectContent>
                                <SelectGroup>
                                  {months.map((month, index) => (
                                    <SelectItem key={index} value={month} alt={"Month" + month} >
                                      {month}
                                    </SelectItem>
                                  ))}
                                </SelectGroup>
                              </SelectContent>
                            </Select>
                            <Select
                              value={day.toString()}
                              onValueChange={(value) =>
                                setDay(parseInt(value, 10))
                              }
                              className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                            >
                              <SelectTrigger className="w-[100px]" aria-label="Select Day">
                                <SelectValue>{day}</SelectValue>
                              </SelectTrigger>
                              <SelectContent>
                                <SelectGroup>
                                  {days.map((day) => (
                                    <SelectItem
                                      key={day}
                                      value={day.toString()}
                                      alt={"Day" + day}
                                    >
                                      {day}
                                    </SelectItem>
                                  ))}
                                </SelectGroup>
                              </SelectContent>
                            </Select>
                          </div>
                        );
                      }}
                    />
                  </FormControl>
                  <FormMessage className="text-red-500">
                    {form.formState.errors.dateOfBirth?.message}
                  </FormMessage>
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="font-semibold text-lg text-gray-700">
                    Email
                  </FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      readOnly={!isEditing}
                      className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />
                  </FormControl>
                  <FormMessage className="text-red-500">
                    {form.formState.errors.email?.message}
                  </FormMessage>
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="phoneNumber"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="font-semibold text-lg text-gray-700">
                    Phone Number
                  </FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      readOnly={!isEditing}
                      className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />
                  </FormControl>
                  <FormMessage className="text-red-500">
                    {form.formState.errors.phoneNumber?.message}
                  </FormMessage>
                </FormItem>
              )}
            />
            <div className="flex flex-col space-y-4">
            <Dialog>
            <DialogTrigger 
                className="font-semibold text-lg text-red-700"
                onClick={() => setPasswordChangeStatus('')}>
                Change Password
            </DialogTrigger>
            <DialogContent>
                <DialogHeader>
                <DialogTitle>Are you absolutely sure?</DialogTitle>
                
                <FormItem className="flex flex-col space-y-2">
                    <FormLabel className="font-semibold text-lg text-gray-700">
                    New Password
                    </FormLabel>
                    <FormControl>
                    <div>
                    <Input
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />

                    <FormLabel className="font-semibold text-lg text-gray-700">
                    Confirm New Password:
                    </FormLabel>
                    
                    <Input
                        type="password"
                        value={newPasswordCheck}
                        onChange={(e) => setNewPasswordCheck(e.target.value)}
                        className="mt-2 p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />
                    </div>
                    </FormControl>
                    <Button
                    type="button"
                    onClick={handlePasswordChange}
                    className="py-1 px-3 bg-gray-800 text-white rounded hover:bg-gray-700"
                    >
                    Change Password:
                    </Button>
                    <p className="font-semibold text-lg text-red-700">{passwordChangeStatus}</p>
                </FormItem> 
                
                </DialogHeader>
            </DialogContent>
            </Dialog>
            <Dialog>
            <DialogTrigger 
                className="font-semibold text-lg text-red-700"
                onClick={() => setDeleteAccountStatus('')}>
                Delete Account
            </DialogTrigger>
            <DialogContent>
                <DialogHeader>
                <DialogTitle>Are you absolutely sure?</DialogTitle>
                
                <FormItem className="flex flex-col space-y-2">
                    <FormLabel className="font-semibold text-lg text-gray-700">
                    Write Password to Delete:
                    </FormLabel>
                    <FormControl>
                    <div>
                    <Input
                    type="password"
                    value={deletePassword}
                    onChange={(e) => setDeletePassword(e.target.value)}
                    className="p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />

                    <FormLabel className="font-semibold text-lg text-gray-700">
                    Confirm Password:
                    </FormLabel>
                    <Input
                    type="password"
                    value={deletePasswordCheck}
                    onChange={(e) => setDeletePasswordCheck(e.target.value)}
                    className="mt-2 p-2 border rounded border-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
                    />
                    </div>
                    </FormControl>
                    <Button
                    type="button"
                    onClick={handleDeleteAccount}
                    className="py-1 px-3 bg-gray-800 text-white rounded hover:bg-gray-700"
                    >
                    Delete Account:
                    </Button>
                    <p className="font-semibold text-lg text-red-700">{passwordChangeStatus}</p>
                </FormItem> 
                
                </DialogHeader>
            </DialogContent>
            </Dialog>
            </div>
          
        </Form>
      </div>
    </div>
  );
}
