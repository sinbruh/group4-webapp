import { getCookie } from "@/tools/cookies";
import React, { useEffect, useState } from "react";
import { asyncApiRequest } from "@/tools/request";
import {Input} from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow
} from "@/components/ui/table";

export default function ConfigurationEditor() {
  const [user, setUser] = useState(null);
  const [cars, setCars] = useState([]);
  const Email = getCookie("current_email");
  const [searchTerm, setSearchTerm] = useState("");

  const updateJsonFile = async () => {
    try {
      //check data
      let data = await asyncApiRequest("GET", "/api/cars");

      if (data) {
        // Add img property to each car configuration
        data = data.map((item) => {
          item.configurations = item.configurations.map((config) => {
            config.img = `${item.make.replace(/ /g, "-")}-${item.model.replace(
              / /g,
              "-"
            )}.webp`;
            return config;
          });
          return item;
        });
      }

      console.log("EditConfigs.js: ", data);

      //update CarCards
      setCars(data);
    } catch (error) {
      console.error("Error updating JSON file:", error);
    }
  };

  useEffect(() => {
    updateJsonFile();
  }, []);

  return (
    <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
      <h2 className={"text-2x1 font-semibold mb-4"}>Car Details</h2>
      <Input
        type="String"
        placeholder="Search"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />

      <form className={"space-y-2"}>
        <Table>
          <TableCaption>A list of Car Details</TableCaption>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Make</TableHead>
              <TableHead>Model</TableHead>
              <TableHead>Config Name</TableHead>
              <TableHead>Seats</TableHead>
              <TableHead>FuelType</TableHead>
              <TableHead>Transmission</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {cars.flatMap((car, index) =>
              car.configurations
                .filter(
                  (config) =>
                    config.id.toString().includes(searchTerm) ||
                    car.make.includes(searchTerm) ||
                    car.model.includes(searchTerm) ||
                    car.valid.toString().includes(searchTerm) ||
                    car.year.toString().includes(searchTerm) ||
                    config.name.includes(searchTerm) ||
                    config.numberOfSeats.toString().includes(searchTerm) ||
                    config.fuelType.includes(searchTerm) ||
                    config.tranmissionType.includes(searchTerm)
                )
                .map((config, configIndex) => (
                  <TableRow key={`${index}-${configIndex}`}>
                    <TableCell className="font-medium">{config.id}</TableCell>
                    <TableCell>{car.make}</TableCell>
                    <TableCell>{car.model}</TableCell>
                    <TableCell>{config.name}</TableCell>
                    <TableCell>{config.numberOfSeats}</TableCell>
                    <TableCell>{config.fuelType}</TableCell>
                    <TableCell>{config.tranmissionType}</TableCell>
                  </TableRow>
                ))
            )}
          </TableBody>
        </Table>
      </form>
    </div>
  );
}