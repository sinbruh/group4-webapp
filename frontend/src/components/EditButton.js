import React, { useState, useEffect } from 'react';
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
  } from "@/components/ui/card"
  import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
  } from "@/components/ui/dialog"
  import { Input } from "@/components/ui/input"
  import { Button } from "@/components/ui/button"
  import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
  } from "@/components/ui/select"



function EditButton({ carInfo: carInfoProp, onEdit }) {
    const [isEditMode, setIsEditMode] = useState(false);
    const [carInfo, setCarInfo] = useState(carInfoProp);

    useEffect(() => {
        setCarInfo(carInfoProp);
    }, [carInfoProp]);

    const handleInputChange = (event) => {
        setCarInfo({
            ...carInfo,
            [event.target.name]: event.target.value
        });
    };

    const handleSave = () => {
        setIsEditMode(false);
        onEdit(carInfo);
    };

    return (
        <div className="p-4">
        <Dialog>
  <DialogTrigger><Button>Edit</Button></DialogTrigger>
  <DialogContent>
    <DialogHeader>
      <DialogTitle>Editing Car</DialogTitle>
      <DialogDescription>
            <Card>
                <CardHeader>

                        <CardDescription>Card Description</CardDescription>
                </CardHeader>

                <CardContent>
                         <p>Price</p>
                </CardContent>
                <CardFooter>
                        <p>Location</p>
                </CardFooter>
                <CardFooter>
                        <p>Fuel Type:</p>
                </CardFooter>
                <CardFooter>
                <Select>
                    <SelectTrigger className="w-[180px]">
                        <SelectValue placeholder="Theme" />
                    </SelectTrigger>

                </Select>
                </CardFooter>
                <CardFooter>
                        <p>Location</p>
                </CardFooter>

            </Card>
      </DialogDescription>
    </DialogHeader>
  </DialogContent>
</Dialog>














            <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onClick={() => setIsEditMode(!isEditMode)}>
                {isEditMode ? 'Save' : 'Edit'}
            </button>
            {isEditMode && (
    <div className="edit-info flex flex-col items-start mt-4 space-y-4 bg-gray-100 p-4 rounded-md shadow-lg">
        <div className="flex space-x-4 items-center">
            <label className="font-bold text-gray-700">Price:</label>
            <input type="text" name="price" value={carInfo.price} onChange={handleInputChange} className="border p-2 rounded-md w-1/2 focus:outline-none focus:ring-2 focus:ring-blue-400" />
        </div>
        <div className="flex space-x-4 items-center">
            <label className="font-bold text-gray-700">Location:</label>
            <input type="text" name="location" value={carInfo.location} onChange={handleInputChange} className="border p-2 rounded-md w-1/2 focus:outline-none focus:ring-2 focus:ring-blue-400" />
        </div>
        <div className="flex space-x-4 items-center">
            <label className="font-bold text-gray-700">Seats:</label>
            <input type="text" name="size" value={carInfo.size} onChange={handleInputChange} className="border p-2 rounded-md w-1/2 focus:outline-none focus:ring-2 focus:ring-blue-400" />
        </div>
        <div className="flex space-x-4 items-center">
            <label className="font-bold text-gray-700">Fuel Type:</label>
            <select name="fuelType" value={carInfo.fuelType} onChange={handleInputChange} className="border p-2 rounded-md w-1/2 focus:outline-none focus:ring-2 focus:ring-blue-400">
                <option value="Petrol">Petrol</option>
                <option value="Diesel">Diesel</option>
                <option value="Electric">Electric</option>
            </select>
        </div>
        <div className="flex space-x-4 items-center">
            <label className="font-bold text-gray-700">Transmission:</label>
            <select name="transmission" value={carInfo.transmission} onChange={handleInputChange} className="border p-2 rounded-md w-1/2 focus:outline-none focus:ring-2 focus:ring-blue-400">
                <option value="Manual">Manual</option>
                <option value="Automatic">Automatic</option>
            </select>
        </div>
        <label className="font-bold text-gray-700 mb-2">Description:
            <textarea name="description" value={carInfo.description} onChange={handleInputChange} className="border p-2 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-400" />
        </label>
        <div className="flex space-x-4 items-center">
            <label className="font-bold text-gray-700">Availability:</label>
            <select name="availability" value={carInfo.availability ? 'Available' : 'Unavailable'} onChange={handleInputChange} className="border p-2 rounded-md w-1/2 focus:outline-none focus:ring-2 focus:ring-blue-400">
                <option value="Available">Available</option>
                <option value="Unavailable">Unavailable</option>
            </select>
        </div>
    </div>
)}
        </div>
    );
}

export default EditButton;
