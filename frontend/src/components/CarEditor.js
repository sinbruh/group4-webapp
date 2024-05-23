import { getCookie } from "@/tools/cookies";
import React, { useEffect, useState } from "react";
import { asyncApiRequest } from "@/tools/request";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useForm } from "react-hook-form";
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
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
const formSchema = z.object({
  make: z.string(),
  model: z.string(),
  year: z.number(),
});

export default function CarEditor() {
  const [cars, setCars] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 5;
  const [carId, setCarId] = useState("");

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      make: "",
      model: "",
      year: "",
    },
  });

  const onSubmit = async (values) => {
    try {
      const response = await asyncApiRequest(
        "PUT",
        `/api/cars/${carId}`,
        values,
        true
      );
      updateJsonFile();

    } catch (error) {
      console.error("Error updating car details: ", error);
    }
  };

  //From CarReader
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

      setCars(data);
    } catch (error) {
      console.error("Error updating JSON file:", error);
    }
  };

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(cars.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    updateJsonFile();
  }, []);


  let sortedCars = [...cars].sort((a, b) => a.id - b.id);

  const getFilteredAndSortedCars = (cars, searchTerm) => {
    return cars
      .filter(
        (car) =>
          car.id.toString().includes(searchTerm) ||
          car.make.includes(searchTerm) ||
          car.model.includes(searchTerm) ||
          car.valid.toString().includes(searchTerm) ||
          car.year.toString().includes(searchTerm)
      )
      .sort((a, b) => a.id - b.id);
  };

  const filteredAndSortedCars = getFilteredAndSortedCars(cars, searchTerm);

  const carsForCurrentPage = filteredAndSortedCars.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
    <div
      className={
        "mx-auto p-6  flex flex-col  overflow-auto"
      }
    >
      <div className={"flex-grow overflow-auto"}>
        <h2 className={"text-2x1 font-semibold mb-4"}>Car Editor</h2>
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
                <TableHead>Make*</TableHead>
                <TableHead>Model*</TableHead>
                <TableHead>Year*</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {carsForCurrentPage
                .map((car, index) => {
                  return (
                    <TableRow key={index}>
                      <TableCell className="font-medium">{car.id}</TableCell>
                      <TableCell>{car.make}</TableCell>
                      <TableCell>{car.model}</TableCell>
                      <TableCell>{car.year}</TableCell>
                    </TableRow>
                  );
                })}
            </TableBody>
          </Table>
          <div className={"p-4 bg-white"}>
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
          </div>
        </form>
        <h2 className={"text-2x1 font-semibold mb-4"}>
          Change a Car by ID below:{" "}
        </h2>
        <Input
          type="text"
          placeholder="Car Id"
          value={carId}
          onChange={(e) => setCarId(e.target.value)}
        />
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
            <FormField
              control={form.control}
              name="make"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Make</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage>
                    {form.formState.errors.make?.message}
                  </FormMessage>
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="model"
              render={({ field }) => (
                <FormItem>
                  <FormLabel htmlFor={field.name}>Model</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage>
                    {form.formState.errors.model?.message}
                  </FormMessage>
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="year"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Year</FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      onChange={(e) => {
                        field.onChange(e);
                        form.setValue("year", parseInt(e.target.value));
                      }}
                    />
                  </FormControl>
                  <FormMessage>
                    {form.formState.errors.Year?.message}
                  </FormMessage>
                </FormItem>
              )}
            />

            <Button type="submit">Submit</Button>
          </form>
        </Form>
      </div>
    </div>
  );
}
