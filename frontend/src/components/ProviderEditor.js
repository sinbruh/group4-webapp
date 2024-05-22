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

export default function ProviderEditor() {
  const [user, setUser] = useState(null);
  const [cars, setCars] = useState([]);
  const Email = getCookie("current_email");
  const [searchTerm, setSearchTerm] = useState("");
  const [providers, setProviders] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 10;

  //From CarReader   Changed to FlatMap Providers
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

      const allProviders = data.flatMap((car) =>
        car.configurations.flatMap((config) =>
          config.providers.map((provider) => ({ ...provider, car, config }))
        )
      );
      setProviders(allProviders);
      setCars(data);
    } catch (error) {
      console.error("Error updating JSON file:", error);
    }
  };

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(providers.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    updateJsonFile();
  }, []);

  const carsForCurrentPage = providers.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
    <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
      <h2 className={"text-2x1 font-semibold mb-4"}>Provider Editor</h2>
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
              <TableHead className="w-[100px]">Provider ID</TableHead>
              <TableHead>Make</TableHead>
              <TableHead>Model</TableHead>
              <TableHead>Config Name</TableHead>
              <TableHead>Provider Name*</TableHead>
              <TableHead>Location*</TableHead>
              <TableHead>Price*</TableHead>
              <TableHead>Visible*</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {carsForCurrentPage
              .filter(
                (provider) =>
                  provider.id.toString() === searchTerm ||
                  provider.car.make.includes(searchTerm) ||
                  provider.car.model.includes(searchTerm) ||
                  provider.config.name.includes(searchTerm) ||
                  provider.name.includes(searchTerm) ||
                  provider.price.toString().includes(searchTerm) ||
                  provider.location.includes(searchTerm)
              )
              .sort((a, b) => a.id - b.id)
              .map((provider, index) => (
                <TableRow key={index}>
                  <TableCell className="font-medium">{provider.id}</TableCell>
                  <TableCell>{provider.car.make}</TableCell>
                  <TableCell>{provider.car.model}</TableCell>
                  <TableCell>{provider.config.name}</TableCell>
                  <TableCell>{provider.name}</TableCell>
                  <TableCell>{provider.location}</TableCell>
                  <TableCell>{provider.price + " kr"}</TableCell>
                  <TableCell>{provider.visible.toString()}</TableCell>
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
