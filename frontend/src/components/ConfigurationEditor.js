import { getCookie } from "@/tools/cookies";
import React, { useEffect, useState } from "react";
import { asyncApiRequest } from "@/tools/request";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

export default function ConfigurationEditor() {
  const [user, setUser] = useState(null);
  const [cars, setCars] = useState([]);
  const Email = getCookie("current_email");
  const [searchTerm, setSearchTerm] = useState("");
  const [configs, setConfigs] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 10;

  //From CarReader    Changed to FlatMap Configurations  
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

      const allConfigs = data.flatMap((car) =>
        car.configurations.map((config) => ({ ...config, car }))
      );
      setConfigs(allConfigs);
      setCars(data);
    } catch (error) {
      console.error("Error updating JSON file:", error);
    }
  };

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(configs.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    updateJsonFile();
  }, []);

  const configsForCurrentPage = configs.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
    <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
      <h2 className={"text-2x1 font-semibold mb-4"}>Configuration Editor</h2>
      <Input
        type="String"
        placeholder="Search"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />

      <form className={"space-y-2"}>
        <Table>
          <TableCaption></TableCaption>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Make</TableHead>
              <TableHead>Model</TableHead>
              <TableHead>Config Name*</TableHead>
              <TableHead>Seats*</TableHead>
              <TableHead>FuelType*</TableHead>
              <TableHead>Transmission*</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {configsForCurrentPage
              .filter(
                (config) =>
                  config.id.toString().includes(searchTerm) ||
                  config.car.make.includes(searchTerm) ||
                  config.car.model.includes(searchTerm) ||
                  config.car.valid.toString().includes(searchTerm) ||
                  config.car.year.toString().includes(searchTerm) ||
                  config.name.includes(searchTerm) ||
                  config.numberOfSeats.toString().includes(searchTerm) ||
                  config.fuelType.includes(searchTerm) ||
                  config.tranmissionType.includes(searchTerm)
              )
              .map((config, index) => (
                <TableRow key={index}>
                  <TableCell className="font-medium">{config.id}</TableCell>
                  <TableCell>{config.car.make}</TableCell>
                  <TableCell>{config.car.model}</TableCell>
                  <TableCell>{config.name}</TableCell>
                  <TableCell>{config.numberOfSeats}</TableCell>
                  <TableCell>{config.fuelType}</TableCell>
                  <TableCell>{config.tranmissionType}</TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
        <Pagination>
          <PaginationContent>
            <PaginationItem>
              <PaginationPrevious onClick={handlePreviousPage} />
            </PaginationItem>
            <PaginationItem>
              <PaginationLink href="#" isActive>
                {currentPage}
              </PaginationLink>
            </PaginationItem>
            <PaginationItem>
              <PaginationNext onClick={handleNextPage} />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </form>
    </div>
  );
}
