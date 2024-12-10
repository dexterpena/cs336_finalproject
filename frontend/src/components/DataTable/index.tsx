import React, { useState, useEffect } from "react";
import Select from "react-select";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Button,
  Box,
  VStack,
  Container,
  HStack,
  Input,
  Text,
  Tag,
  TagLabel,
  TagCloseButton,
  FormControl,
  FormLabel,
  Grid,
  GridItem,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
  Spinner,
  Center,
  useToast,
} from "@chakra-ui/react";
import axios from "axios";

const customStyles = {
  control: (provided) => ({
    ...provided,
    backgroundColor: "transparent",
    borderColor: "gray.300",
    "&:hover": {
      borderColor: "gray.400",
    },
  }),
  menu: (provided) => ({
    ...provided,
    backgroundColor: "white",
  }),
  option: (provided, state) => ({
    ...provided,
    backgroundColor: state.isSelected
      ? "blue.500"
      : state.isFocused
      ? "blue.100"
      : "white",
    color: "black",
    cursor: "pointer",
    "&:hover": {
      backgroundColor: state.isSelected ? "blue.600" : "blue.200",
      color: state.isSelected ? "white" : "black",
    },
  }),
  singleValue: (provided) => ({
    ...provided,
    color: "black",
  }),
  multiValue: (provided) => ({
    ...provided,
    backgroundColor: "blue.100",
  }),
  multiValueLabel: (provided) => ({
    ...provided,
    color: "blue.700",
  }),
  multiValueRemove: (provided) => ({
    ...provided,
    color: "blue.700",
    cursor: "pointer",
    "&:hover": {
      backgroundColor: "blue.200",
      color: "blue.800",
    },
  }),
};

const ITEMS_PER_PAGE = 100;

const initialFilters = {
  msamd: [],
  incomeToDebtRatioMin: "",
  incomeToDebtRatioMax: "",
  county: [],
  loanType: [],
  tractToMsamdIncomeMin: "",
  tractToMsamdIncomeMax: "",
  loanPurpose: [],
  propertyType: [],
  ownerOccupied: "",
};

const FRONTEND_ITEMS_PER_PAGE = 100;
const BACKEND_ITEMS_PER_PAGE = 1000;

export const DataTable = () => {
  const toast = useToast();

  const [currentPage, setCurrentPage] = useState(1);
  const [filters, setFilters] = useState(initialFilters);
  const [changeFilters, setChangeFilters] = useState(initialFilters);
  const [data, setData] = useState([]);
  const [totalPages, setTotalPages] = useState(1);
  const [totalItems, setTotalItems] = useState(0);
  const [loanAmountSum, setLoanAmountSum] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isAcceptingRate, setIsAcceptingRate] = useState(false);
  const { isOpen, onOpen, onClose } = useDisclosure();
  const {
    isOpen: isCreateMortgageOpen,
    onOpen: onOpenCreateMortgage,
    onClose: onCloseCreateMortgage,
  } = useDisclosure();

  const {
    isOpen: isRateModalOpen,
    onOpen: onOpenRateModal,
    onClose: onCloseRateModal,
  } = useDisclosure();
  const [isCalculatingRate, setIsCalculatingRate] = useState(false);
  const [rateResult, setRateResult] = useState({ rate: null, cost: null });

  const [loadedDataRanges, setLoadedDataRanges] = useState([]);
  const [allData, setAllData] = useState([]);

  const [mortgageForm, setMortgageForm] = useState({
    income: null,
    loanAmount: null,
    msamd: null,
    sex: null,
    ethnicity: null,
    loanType: null,
  });

  const handleCalculateRate = async () => {
    try {
      setIsCalculatingRate(true);

      let filterString = "";

      if (filters.msamd.length) {
        filterString += `&msamd=${filters.msamd.map((f) => f.value).join(",")}`;
      }

      if (filters.county.length) {
        filterString += `&counties=${filters.county
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.propertyType.length) {
        filterString += `&propertyTypes=${filters.propertyType
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.loanType.length) {
        filterString += `&loanTypes=${filters.loanType
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.loanPurpose.length) {
        filterString += `&loanPurposes=${filters.loanPurpose
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.incomeToDebtRatioMin.length) {
        filterString += `&minIncomeDebtRatio=${filters.incomeToDebtRatioMin}`;
      } else if (filters.incomeToDebtRatioMax.length) {
        filterString += `&minIncomeDebtRatio=0`;
      }

      if (filters.incomeToDebtRatioMax.length) {
        filterString += `&maxIncomeDebtRatio=${filters.incomeToDebtRatioMax}`;
      } else if (filters.incomeToDebtRatioMin) {
        filterString += `&maxIncomeDebtRatio=100}`;
      }

      if (filters.tractToMsamdIncomeMin.length) {
        filterString += `&minTractIncome=${filters.tractToMsamdIncomeMin}`;
      }

      if (filters.tractToMsamdIncomeMax.length) {
        filterString += `&maxTractIncome=${filters.tractToMsamdIncomeMax}`;
      }

      if (filters.ownerOccupied.length) {
        filterString += `&ownerOccupancy=${
          filters.ownerOccupied == "Yes" ? 1 : 0
        }`;
      }

      const req =
        filterString.length > 0
          ? `http://localhost:8080/api/calculate-rate?${filterString.substring(
              1
            )}`
          : `http://localhost:8080/api/calculate-rate`;

      const response = await axios.get(req);
      setRateResult(response.data);
      onOpenRateModal();
    } catch (error) {
      console.error("Error calculating rate:", error);
      toast({
        title: "Error",
        description: "Failed to calculate rate. Please try again.",
        status: "error",
        duration: 5000,
        isClosable: true,
        position: "top",
      });
    } finally {
      setIsCalculatingRate(false);
    }
  };

  const handleMortgageFormChange = (e) => {
    const { name, value } = e.target;
    setMortgageForm((prevForm) => ({
      ...prevForm,
      [name]: value,
    }));
  };

  const handleSelectChangeMortgage = (selectedOption, actionMeta) => {
    const { name } = actionMeta;
    setMortgageForm((prevForm) => ({
      ...prevForm,
      [name]: selectedOption.value ? selectedOption : null,
    }));
  };

  const handleSubmitMortgage = async () => {
    try {
      setIsSubmitting(true);

      let invalidInput = false;
      for (const key in mortgageForm) {
        if (mortgageForm[key] == null) {
          toast({
            title: "Error",
            description: "You must specify all fields.",
            status: "error",
            duration: 5000,
            isClosable: true,
            position: "top",
          });
          invalidInput = true;
          return;
        }
      }
      if (invalidInput) return;

      const requestBody = {
        applicantIncome000s: Number(mortgageForm.income),
        loanAmount000s: Number(mortgageForm.loanAmount),
        msamd: Number(mortgageForm.msamd.value),
        applicantSex: Number(mortgageForm.sex.value),
        applicantEthnicity: Number(mortgageForm.ethnicity.value),
        loanType: Number(mortgageForm.loanType.value),
      };

      const response = await axios.post(
        "http://localhost:8080/api/create-mortgage",
        requestBody
      );

      if (response.status === 200) {
        onCloseCreateMortgage();
        setMortgageForm({
          income: null,
          loanAmount: null,
          msamd: null,
          sex: null,
          ethnicity: null,
          loanType: null,
        });

        setAllData([]);
        setLoadedDataRanges([]);
        setCurrentPage(1);

        toast({
          title: "Success",
          description: "Mortgage created successfully!",
          status: "success",
          duration: 5000,
          isClosable: true,
          position: "top",
        });
      }
    } catch (error) {
      console.error("Error creating mortgage:", error);
      toast({
        title: "Error",
        description: "Failed to create mortgage. Please try again.",
        status: "error",
        duration: 5000,
        isClosable: true,
        position: "top",
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleAcceptRate = async () => {
    try {
      setIsAcceptingRate(true);

      let filterString = "";
      if (filters.msamd.length) {
        filterString += `&msamd=${filters.msamd.map((f) => f.value).join(",")}`;
      }

      if (filters.county.length) {
        filterString += `&counties=${filters.county
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.propertyType.length) {
        filterString += `&propertyTypes=${filters.propertyType
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.loanType.length) {
        filterString += `&loanTypes=${filters.loanType
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.loanPurpose.length) {
        filterString += `&loanPurposes=${filters.loanPurpose
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.incomeToDebtRatioMin.length) {
        filterString += `&minIncomeDebtRatio=${filters.incomeToDebtRatioMin}`;
      } else if (filters.incomeToDebtRatioMax.length) {
        filterString += `&minIncomeDebtRatio=0`;
      }

      if (filters.incomeToDebtRatioMax.length) {
        filterString += `&maxIncomeDebtRatio=${filters.incomeToDebtRatioMax}`;
      } else if (filters.incomeToDebtRatioMin) {
        filterString += `&maxIncomeDebtRatio=100}`;
      }

      if (filters.tractToMsamdIncomeMin.length) {
        filterString += `&minTractIncome=${filters.tractToMsamdIncomeMin}`;
      }

      if (filters.tractToMsamdIncomeMax.length) {
        filterString += `&maxTractIncome=${filters.tractToMsamdIncomeMax}`;
      }

      if (filters.ownerOccupied.length) {
        filterString += `&ownerOccupancy=${
          filters.ownerOccupied == "Yes" ? 1 : 0
        }`;
      }

      const req =
        filterString.length > 0
          ? `http://localhost:8080/api/accept-rate?${filterString.substring(1)}`
          : `http://localhost:8080/api/accept-rate`;

      const response = await axios.post(req);

      // Reset data
      setAllData([]);
      setLoadedDataRanges([]);
      setCurrentPage(1);

      // Close modal
      onCloseRateModal();

      // Show success toast
      toast({
        title: "Success",
        description: `Successfully updated ${response.data.count} mortgages`,
        status: "success",
        duration: 5000,
        isClosable: true,
        position: "top",
      });
    } catch (error) {
      console.error("Error accepting rate:", error);
      toast({
        title: "Error",
        description: "Failed to accept rate. Please try again.",
        status: "error",
        duration: 5000,
        isClosable: true,
        position: "top",
      });
    } finally {
      setIsAcceptingRate(false);
    }
  };

  const fetchData = async (pageNum) => {
    try {
      setIsLoading(true);

      let filterString = "";

      if (filters.msamd.length) {
        filterString += `&msamd=${filters.msamd.map((f) => f.value).join(",")}`;
      }

      if (filters.county.length) {
        filterString += `&counties=${filters.county
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.propertyType.length) {
        filterString += `&propertyTypes=${filters.propertyType
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.loanType.length) {
        filterString += `&loanTypes=${filters.loanType
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.loanPurpose.length) {
        filterString += `&loanPurposes=${filters.loanPurpose
          .map((f) => f.value)
          .join(",")}`;
      }

      if (filters.incomeToDebtRatioMin.length) {
        filterString += `&minIncomeDebtRatio=${filters.incomeToDebtRatioMin}`;
      } else if (filters.incomeToDebtRatioMax.length) {
        filterString += `&minIncomeDebtRatio=0`;
      }

      if (filters.incomeToDebtRatioMax.length) {
        filterString += `&maxIncomeDebtRatio=${filters.incomeToDebtRatioMax}`;
      } else if (filters.incomeToDebtRatioMin) {
        filterString += `&maxIncomeDebtRatio=100}`;
      }

      if (filters.tractToMsamdIncomeMin.length) {
        filterString += `&minTractIncome=${filters.tractToMsamdIncomeMin}`;
      }

      if (filters.tractToMsamdIncomeMax.length) {
        filterString += `&maxTractIncome=${filters.tractToMsamdIncomeMax}`;
      }

      if (filters.ownerOccupied.length) {
        filterString += `&ownerOccupancy=${
          filters.ownerOccupied == "Yes" ? 1 : 0
        }`;
      }

      const req =
        filterString.length > 0
          ? `http://localhost:8080/api/mortgages?page=${pageNum}${filterString}`
          : `http://localhost:8080/api/mortgages?page=${pageNum}`;
      const response = await axios.get(req);
      const {
        data: responseData,
        count,
        totalPages: backendTotalPages,
        loanAmountSum: loanAmountSumFromApi,
      } = response.data;

      const frontendTotalPages = Math.ceil(count / FRONTEND_ITEMS_PER_PAGE);

      setLoadedDataRanges((prev) => [
        ...prev,
        {
          start: (pageNum - 1) * BACKEND_ITEMS_PER_PAGE,
          end: (pageNum - 1) * BACKEND_ITEMS_PER_PAGE + responseData.length - 1,
          page: pageNum,
        },
      ]);

      setAllData((prev) => {
        const newData = [...prev];
        responseData.forEach((item, index) => {
          const absoluteIndex = (pageNum - 1) * BACKEND_ITEMS_PER_PAGE + index;
          newData[absoluteIndex] = item;
        });
        return newData;
      });

      setTotalItems(count);
      setTotalPages(frontendTotalPages);
      setLoanAmountSum(loanAmountSumFromApi);
    } catch (error) {
      console.error("Error fetching mortgage data:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    setAllData([]);
    setLoadedDataRanges([]);
    setCurrentPage(1);
  }, [filters]);

  useEffect(() => {
    const frontendStartIndex = (currentPage - 1) * FRONTEND_ITEMS_PER_PAGE;
    const frontendEndIndex = frontendStartIndex + FRONTEND_ITEMS_PER_PAGE - 1;

    const backendStartPage =
      Math.floor(frontendStartIndex / BACKEND_ITEMS_PER_PAGE) + 1;
    const backendEndPage =
      Math.floor(frontendEndIndex / BACKEND_ITEMS_PER_PAGE) + 1;

    const needsLoading = !loadedDataRanges.some(
      (range) =>
        range.start <= frontendStartIndex && range.end >= frontendEndIndex
    );

    if (needsLoading) {
      const pagesToLoad = new Set();
      for (let i = backendStartPage; i <= backendEndPage; i++) {
        if (!loadedDataRanges.some((range) => range.page === i)) {
          pagesToLoad.add(i);
        }
      }

      pagesToLoad.forEach((page) => {
        fetchData(page);
      });
    }
  }, [currentPage, loadedDataRanges]);

  const getCurrentPageData = () => {
    const startIndex = (currentPage - 1) * FRONTEND_ITEMS_PER_PAGE;
    const endIndex = startIndex + FRONTEND_ITEMS_PER_PAGE;
    return allData.slice(startIndex, endIndex).filter(Boolean);
  };

  const currentData = getCurrentPageData();

  const handleNextPage = () => {
    setCurrentPage((prev) => Math.min(prev + 1, totalPages));
  };

  const handlePreviousPage = () => {
    setCurrentPage((prev) => Math.max(prev - 1, 1));
  };

  const handlePageChange = (event) => {
    const pageNumber = Number(event.target.value);
    if (pageNumber >= 1 && pageNumber <= totalPages) {
      setCurrentPage(pageNumber);
    }
  };

  const handleJumpToPage = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleFilterChange = (event) => {
    const { name, value } = event.target;
    setChangeFilters((prevFilters) => ({
      ...filters,
      ...prevFilters,
      [name]: value,
    }));
  };

  const handleSelectChange = (selectedOptions, actionMeta) => {
    const { name } = actionMeta;
    setChangeFilters((prevFilters) => ({
      ...filters,
      ...prevFilters,
      [name]: Array.isArray(selectedOptions)
        ? selectedOptions.map((o) => ({ value: o.value, label: o.label }))
        : selectedOptions?.value || "",
    }));
  };

  const handleRemoveFilter = (filterName) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [filterName]: Array.isArray(prevFilters[filterName]) ? [] : "",
    }));
    setChangeFilters((prevFilters) => ({
      ...prevFilters,
      [filterName]: Array.isArray(prevFilters[filterName]) ? [] : "",
    }));
    setCurrentPage(1);
  };

  const handleClearFilters = () => {
    setFilters(initialFilters);
    setChangeFilters(initialFilters);
    setCurrentPage(1);
  };

  const handleApplyFilters = () => {
    onClose();
    setFilters(changeFilters);
    setCurrentPage(1);
  };

  const renderPageNumbers = () => {
    const pageNumbers = [];
    const maxPageButtons = 5;
    const halfMaxPageButtons = Math.floor(maxPageButtons / 2);

    if (totalPages <= maxPageButtons) {
      for (let i = 1; i <= totalPages; i++) {
        pageNumbers.push(i);
      }
    } else {
      if (currentPage <= halfMaxPageButtons) {
        for (let i = 1; i <= maxPageButtons; i++) {
          pageNumbers.push(i);
        }
        pageNumbers.push("...");
        pageNumbers.push(totalPages);
      } else if (currentPage > totalPages - halfMaxPageButtons) {
        pageNumbers.push(1);
        pageNumbers.push("...");
        for (let i = totalPages - maxPageButtons + 1; i <= totalPages; i++) {
          pageNumbers.push(i);
        }
      } else {
        pageNumbers.push(1);
        pageNumbers.push("...");
        for (
          let i = currentPage - halfMaxPageButtons;
          i <= currentPage + halfMaxPageButtons;
          i++
        ) {
          pageNumbers.push(i);
        }
        pageNumbers.push("...");
        pageNumbers.push(totalPages);
      }
    }

    return pageNumbers;
  };

  return (
    <Box w="full" overflow="hidden" position="relative">
      <Container py="32px" maxW="full">
        <VStack spacing="32px">
          <HStack spacing="8px" w="full" justifyContent="space-between">
            <HStack spacing="8px">
              <Button
                onClick={handleCalculateRate}
                colorScheme="yellow"
                size="sm"
                isLoading={isCalculatingRate}
                loadingText="Calculating"
                disabled={isCalculatingRate}
              >
                Calculate Rate
              </Button>
              <Button
                onClick={onOpenCreateMortgage}
                colorScheme="green"
                size="sm"
              >
                Create Mortgage
              </Button>
              <Button onClick={onOpen} colorScheme="blue" size="sm">
                Apply Filters
              </Button>
              <Button size="sm" onClick={handleClearFilters}>
                Clear All
              </Button>
            </HStack>
            <HStack spacing="8px">
              {Object.keys(filters).map((filterName) =>
                filters[filterName] && filters[filterName].length > 0 ? (
                  <Tag
                    key={filterName}
                    size="sm"
                    variant="solid"
                    colorScheme="blue"
                  >
                    <TagLabel>{`${filterName}: ${
                      Array.isArray(filters[filterName])
                        ? filters[filterName].map((f) => f.label).join(", ")
                        : filters[filterName]
                    }`}</TagLabel>
                    <TagCloseButton
                      onClick={() => handleRemoveFilter(filterName)}
                    />
                  </Tag>
                ) : null
              )}
            </HStack>
          </HStack>
          <PaginationBar
            currentPage={currentPage}
            totalPages={totalPages}
            handlePreviousPage={handlePreviousPage}
            handleNextPage={handleNextPage}
            handlePageChange={handlePageChange}
            handleJumpToPage={handleJumpToPage}
            totalItems={totalItems}
            renderPageNumbers={renderPageNumbers}
          />
          <Text fontSize="lg" fontWeight="bold">
            Loan Amount Sum: {loanAmountSum.toLocaleString()}
          </Text>

          {isLoading ? (
            <Center w="full" h="400px">
              <Spinner
                thickness="4px"
                speed="0.65s"
                emptyColor="gray.200"
                color="blue.500"
                size="xl"
              />
            </Center>
          ) : (
            <TableContainer w="full">
              <Table variant="simple" w="full">
                <Thead>
                  <Tr>
                    <Th>ID</Th>
                    <Th>As of Year</Th>
                    <Th>Agency</Th>
                    <Th>Loan Type</Th>
                    <Th>Property Type</Th>
                    <Th>Loan Purpose</Th>
                    <Th>Owner Occupany</Th>
                    <Th>Loan Amount</Th>
                    <Th>Preapproval</Th>
                    <Th>Action Taken</Th>
                    <Th>MSAMD</Th>
                    <Th>State</Th>
                    <Th>County</Th>
                    <Th>Census Tract Number</Th>
                    <Th>Applicant Ethnicity</Th>
                    <Th>Co-Applicant Ethnicity</Th>
                    <Th>Applicant Race</Th>
                    <Th>Co-Applicant Race</Th>
                    <Th>Applicant Sex</Th>
                    <Th>Co-Applicant Sex</Th>
                    <Th>Applicant Income</Th>
                    <Th>Purchaser Type</Th>
                    <Th>Denial Reason</Th>
                    <Th>Rate Spread</Th>
                    <Th>HOEPA Status</Th>
                    <Th>Lien Status</Th>
                    <Th>Population</Th>
                    <Th>Minority Population</Th>
                    <Th>HUD Median Family Income</Th>
                    <Th>Tract to MSAMD Income</Th>
                    <Th>Owner Occupied Units</Th>
                    <Th>1-4 Family Units</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {currentData.map((d, index) => (
                    <Tr key={index}>
                      <Td>{d.applicationId}</Td>
                      <Td>{d.asOfYear}</Td>
                      <Td>{d.agencyName}</Td>
                      <Td>{d.loanTypeName}</Td>
                      <Td>{d.propertyTypeName}</Td>
                      <Td>{d.loanPurposeName}</Td>
                      <Td>{d.ownerOccupancyName}</Td>
                      <Td>{d.loanAmount000s}</Td>
                      <Td>{d.preapprovalName}</Td>
                      <Td>{d.actionTakenName}</Td>
                      <Td>{d.msamdName}</Td>
                      <Td>{d.stateName}</Td>
                      <Td>{d.countyName}</Td>
                      <Td>{d.censusTractNumber}</Td>
                      <Td>{d.applicantEthnicityName}</Td>
                      <Td>{d.coApplicantEthnicityName}</Td>
                      <Td>
                        {d.applicantRaceName1} {d.applicantRaceName2}{" "}
                        {d.applicantRaceName3} {d.applicantRaceName4}{" "}
                        {d.applicantRaceName5}
                      </Td>
                      <Td>
                        {d.coApplicantRaceName1} {d.coApplicantRaceName2}{" "}
                        {d.coApplicantRaceName3} {d.coApplicantRaceName4}{" "}
                        {d.coApplicantRaceName5}
                      </Td>
                      <Td>{d.applicantSexName}</Td>
                      <Td>{d.coApplicantSexName}</Td>
                      <Td>{d.applicantIncome000s}</Td>
                      <Td>{d.purchaserTypeName}</Td>
                      <Td>
                        {d.denialReasonName1} {d.denialReasonName2}{" "}
                        {d.denialReasonName3}
                      </Td>
                      <Td>{d.rateSpread}</Td>
                      <Td>{d.hoepaStatusName}</Td>
                      <Td>{d.lienStatusName}</Td>
                      <Td>{d.population}</Td>
                      <Td>{d.minorityPopulation}</Td>
                      <Td>{d.hudMedianFamilyIncome}</Td>
                      <Td>{d.tractToMsamdIncome}</Td>
                      <Td>{d.numberOfOwnerOccupiedUnits}</Td>
                      <Td>{d.numberOf1To4FamilyUnits}</Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            </TableContainer>
          )}

          {!isLoading && (
            <PaginationBar
              currentPage={currentPage}
              totalPages={totalPages}
              handlePreviousPage={handlePreviousPage}
              handleNextPage={handleNextPage}
              handlePageChange={handlePageChange}
              handleJumpToPage={handleJumpToPage}
              totalItems={totalItems}
              renderPageNumbers={renderPageNumbers}
            />
          )}
        </VStack>
      </Container>

      <Modal isOpen={isRateModalOpen} onClose={onCloseRateModal}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Rate Calculation Results</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <VStack spacing={4} align="stretch">
              <Box>
                <Text fontWeight="bold">Rate:</Text>
                <Text fontSize="xl">{rateResult.rate}%</Text>
              </Box>
              <Box>
                <Text fontWeight="bold">Cost:</Text>
                <Text fontSize="xl">${rateResult.cost?.toLocaleString()}</Text>
              </Box>
            </VStack>
          </ModalBody>
          <ModalFooter>
            <Button
              colorScheme="blue"
              mr={3}
              onClick={handleAcceptRate}
              isLoading={isAcceptingRate}
              loadingText="Accepting"
              disabled={isAcceptingRate}
            >
              Accept
            </Button>
            <Button
              variant="ghost"
              onClick={onCloseRateModal}
              disabled={isAcceptingRate}
            >
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>

      <Modal isOpen={isOpen} onClose={onClose} size="xl">
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Filter Options</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Grid templateColumns="repeat(2, 1fr)" gap={6}>
              <GridItem colSpan={2}>
                <FormControl>
                  <FormLabel>MSAMD</FormLabel>
                  <Select
                    isMulti
                    placeholder="Select MSAMD"
                    name="msamd"
                    value={changeFilters.msamd.map((v) => ({
                      value: v.value,
                      label: v.label,
                    }))}
                    onChange={(selectedOptions) =>
                      handleSelectChange(selectedOptions, { name: "msamd" })
                    }
                    options={[
                      { value: "1", label: "Newark - NJ, PA" },
                      { value: "2", label: "Trenton - NJ" },
                      {
                        value: "3",
                        label: "New York, Jersey City, White Plains - NY, NJ",
                      },
                      { value: "4", label: "Wilmington - DE, MD, NJ" },
                      { value: "5", label: "Vineland, Bridgeton - NJ" },
                      { value: "6", label: "Ocean City - NJ" },
                      { value: "7", label: "Camden - NJ" },
                      { value: "8", label: "Atlantic City, Hammonton - NJ" },
                      {
                        value: "9",
                        label: "Allentown, Bethlehem, Easton - PA, NJ",
                      },
                    ]}
                    styles={customStyles}
                  />
                </FormControl>
              </GridItem>
              <GridItem>
                <FormControl>
                  <FormLabel>Income to Debt Ratio Min</FormLabel>
                  <Input
                    placeholder="Min Ratio"
                    name="incomeToDebtRatioMin"
                    value={changeFilters.incomeToDebtRatioMin}
                    onChange={handleFilterChange}
                    size="sm"
                  />
                </FormControl>
              </GridItem>
              <GridItem>
                <FormControl>
                  <FormLabel>Income to Debt Ratio Max</FormLabel>
                  <Input
                    placeholder="Max Ratio"
                    name="incomeToDebtRatioMax"
                    value={changeFilters.incomeToDebtRatioMax}
                    onChange={handleFilterChange}
                    size="sm"
                  />
                </FormControl>
              </GridItem>
              <GridItem colSpan={2}>
                <FormControl>
                  <FormLabel>County</FormLabel>
                  <Select
                    isMulti
                    placeholder="Select County"
                    name="county"
                    value={changeFilters.county.map((v) => ({
                      value: v.value,
                      label: v.label,
                    }))}
                    onChange={(selectedOptions) =>
                      handleSelectChange(selectedOptions, { name: "county" })
                    }
                    options={[
                      { value: "1", label: "Atlantic County" },
                      { value: "2", label: "Bergen County" },
                      { value: "3", label: "Burlington County" },
                      { value: "4", label: "Camden County" },
                      { value: "5", label: "Cape May County" },
                      { value: "6", label: "Cumberland County" },
                      { value: "7", label: "Essex County" },
                      { value: "8", label: "Gloucester County" },
                      { value: "9", label: "Hudson County" },
                      { value: "10", label: "Hunterdon County" },
                      { value: "11", label: "Mercer County" },
                      { value: "12", label: "Middlesex County" },
                      { value: "13", label: "Monmouth County" },
                      { value: "14", label: "Morris County" },
                      { value: "15", label: "Ocean County" },
                      { value: "16", label: "Passaic County" },
                      { value: "17", label: "Salem County" },
                      { value: "18", label: "Somerset County" },
                      { value: "19", label: "Sussex County" },
                      { value: "20", label: "Union County" },
                      { value: "21", label: "Warren County" },
                    ]}
                    styles={customStyles}
                  />
                </FormControl>
              </GridItem>
              <GridItem colSpan={2}>
                <FormControl>
                  <FormLabel>Loan Type</FormLabel>
                  <Select
                    isMulti
                    placeholder="Select Loan Type"
                    name="loanType"
                    value={changeFilters.loanType.map((v) => ({
                      value: v.value,
                      label: v.label,
                    }))}
                    onChange={(selectedOptions) =>
                      handleSelectChange(selectedOptions, { name: "loanType" })
                    }
                    options={[
                      { value: "1", label: "VA-guaranteed" },
                      { value: "2", label: "FSA/RHS-guaranteed" },
                      { value: "3", label: "Conventional" },
                      { value: "4", label: "FHA-insured" },
                    ]}
                    styles={customStyles}
                  />
                </FormControl>
              </GridItem>
              <GridItem>
                <FormControl>
                  <FormLabel>Tract to MSAMD Income Min</FormLabel>
                  <Input
                    placeholder="Min Income"
                    name="tractToMsamdIncomeMin"
                    value={changeFilters.tractToMsamdIncomeMin}
                    onChange={handleFilterChange}
                    size="sm"
                  />
                </FormControl>
              </GridItem>
              <GridItem>
                <FormControl>
                  <FormLabel>Tract to MSAMD Income Max</FormLabel>
                  <Input
                    placeholder="Max Income"
                    name="tractToMsamdIncomeMax"
                    value={changeFilters.tractToMsamdIncomeMax}
                    onChange={handleFilterChange}
                    size="sm"
                  />
                </FormControl>
              </GridItem>
              <GridItem colSpan={2}>
                <FormControl>
                  <FormLabel>Loan Purpose</FormLabel>
                  <Select
                    isMulti
                    placeholder="Select Loan Purpose"
                    name="loanPurpose"
                    value={changeFilters.loanPurpose.map((v) => ({
                      value: v.value,
                      label: v.label,
                    }))}
                    onChange={(selectedOptions) =>
                      handleSelectChange(selectedOptions, {
                        name: "loanPurpose",
                      })
                    }
                    options={[
                      { value: "1", label: "Home improvement" },
                      { value: "2", label: "Refinancing" },
                      { value: "3", label: "Home purchase" },
                    ]}
                    styles={customStyles}
                  />
                </FormControl>
              </GridItem>
              <GridItem colSpan={2}>
                <FormControl>
                  <FormLabel>Property Type</FormLabel>
                  <Select
                    isMulti
                    placeholder="Select Property Type"
                    name="propertyType"
                    value={changeFilters.propertyType.map((v) => ({
                      value: v.value,
                      label: v.label,
                    }))}
                    onChange={(selectedOptions) =>
                      handleSelectChange(selectedOptions, {
                        name: "propertyType",
                      })
                    }
                    options={[
                      { value: "1", label: "Manufactured housing" },
                      { value: "2", label: "Multifamily dwelling" },
                      {
                        value: "3",
                        label:
                          "One-to-four family dwelling (other than manufactured housing)",
                      },
                    ]}
                    styles={customStyles}
                  />
                </FormControl>
              </GridItem>
              <GridItem>
                <FormControl>
                  <FormLabel>Owner Occupied</FormLabel>
                  <Select
                    placeholder="Owner Occupied"
                    name="ownerOccupied"
                    isClearable={true}
                    value={
                      changeFilters.ownerOccupied
                        ? {
                            value: changeFilters.ownerOccupied,
                            label: changeFilters.ownerOccupied,
                          }
                        : null
                    }
                    onChange={(selectedOption) =>
                      handleSelectChange(selectedOption, {
                        name: "ownerOccupied",
                      })
                    }
                    options={[
                      { value: "Yes", label: "Yes" },
                      { value: "No", label: "No" },
                    ]}
                    styles={customStyles}
                  />
                </FormControl>
              </GridItem>
            </Grid>
          </ModalBody>
          <ModalFooter>
            <Button colorScheme="blue" mr={3} onClick={handleApplyFilters}>
              Apply
            </Button>
            <Button variant="ghost" onClick={onClose}>
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>

      <Modal
        isOpen={isCreateMortgageOpen}
        onClose={onCloseCreateMortgage}
        size="lg"
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Create Mortgage</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <FormControl mb={4}>
              <FormLabel>Income</FormLabel>
              <Input
                type="number"
                name="income"
                value={mortgageForm.income}
                onChange={handleMortgageFormChange}
              />
            </FormControl>
            <FormControl mb={4}>
              <FormLabel>Loan Amount</FormLabel>
              <Input
                type="number"
                name="loanAmount"
                value={mortgageForm.loanAmount}
                onChange={handleMortgageFormChange}
              />
            </FormControl>
            <FormControl mb={4}>
              <FormLabel>MSAMD</FormLabel>
              <Select
                placeholder="Select MSAMD"
                name="msamd"
                value={mortgageForm.msamd}
                onChange={(selectedOption) =>
                  handleSelectChangeMortgage(selectedOption, { name: "msamd" })
                }
                options={[
                  { value: "1", label: "Newark - NJ, PA" },
                  { value: "2", label: "Trenton - NJ" },
                  {
                    value: "3",
                    label: "New York, Jersey City, White Plains - NY, NJ",
                  },
                  { value: "4", label: "Wilmington - DE, MD, NJ" },
                  { value: "5", label: "Vineland, Bridgeton - NJ" },
                  { value: "6", label: "Ocean City - NJ" },
                  { value: "7", label: "Camden - NJ" },
                  { value: "8", label: "Atlantic City, Hammonton - NJ" },
                  {
                    value: "9",
                    label: "Allentown, Bethlehem, Easton - PA, NJ",
                  },
                ]}
                styles={customStyles}
              />
            </FormControl>
            <FormControl mb={4}>
              <FormLabel>Sex</FormLabel>
              <Select
                placeholder="Select Sex"
                name="sex"
                value={mortgageForm.sex}
                onChange={(selectedOption) =>
                  handleSelectChangeMortgage(selectedOption, { name: "sex" })
                }
                options={[
                  { value: "1", label: "Male" },
                  { value: "2", label: "Female" },
                  { value: "3", label: "Information not provided" },
                ]}
                styles={customStyles}
              />
            </FormControl>
            <FormControl mb={4}>
              <FormLabel>Ethnicity</FormLabel>
              <Select
                placeholder="Select Ethnicity"
                name="ethnicity"
                value={mortgageForm.ethnicity}
                onChange={(selectedOption) =>
                  handleSelectChangeMortgage(selectedOption, {
                    name: "ethnicity",
                  })
                }
                options={[
                  { value: "1", label: "Hispanic or Latino" },
                  { value: "2", label: "Not Hispanic or Latino" },
                  { value: "3", label: "Information not provided" },
                ]}
                styles={customStyles}
              />
            </FormControl>
            <FormControl mb={4}>
              <FormLabel>Loan Type</FormLabel>
              <Select
                placeholder="Select Loan Type"
                name="loanType"
                value={mortgageForm.loanType}
                onChange={(selectedOption) =>
                  handleSelectChangeMortgage(selectedOption, {
                    name: "loanType",
                  })
                }
                options={[
                  { value: "1", label: "VA-guaranteed" },
                  { value: "2", label: "FSA/RHS-guaranteed" },
                  { value: "3", label: "Conventional" },
                  { value: "4", label: "FHA-insured" },
                ]}
                styles={customStyles}
              />
            </FormControl>
          </ModalBody>
          <ModalFooter>
            <Button
              colorScheme="blue"
              mr={3}
              onClick={handleSubmitMortgage}
              isLoading={isSubmitting}
              loadingText="Submitting"
              disabled={isSubmitting}
            >
              Submit
            </Button>
            <Button
              variant="ghost"
              onClick={onCloseCreateMortgage}
              disabled={isSubmitting}
            >
              Cancel
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </Box>
  );
};

const PaginationBar = ({
  currentPage,
  totalPages,
  handlePreviousPage,
  handleNextPage,
  handlePageChange,
  handleJumpToPage,
  totalItems,
  renderPageNumbers,
}) => {
  const startItem = (currentPage - 1) * ITEMS_PER_PAGE + 1;
  const endItem = Math.min(currentPage * ITEMS_PER_PAGE, totalItems);

  return (
    <HStack spacing="8px" justifyContent="space-between" w="full" fontSize="sm">
      <HStack spacing="8px">
        <Button
          onClick={handlePreviousPage}
          disabled={currentPage === 1}
          size="sm"
        >
          Previous
        </Button>
        {renderPageNumbers().map((pageNumber, index) =>
          pageNumber === "..." ? (
            <Text key={index} fontSize="sm">
              ...
            </Text>
          ) : (
            <Button
              key={index}
              onClick={() => handleJumpToPage(pageNumber)}
              variant={currentPage === pageNumber ? "solid" : "outline"}
              size="sm"
            >
              {pageNumber}
            </Button>
          )
        )}
        <Button
          onClick={handleNextPage}
          disabled={currentPage === totalPages}
          size="sm"
        >
          Next
        </Button>
        <Input
          type="number"
          value={currentPage}
          onChange={handlePageChange}
          min={1}
          max={totalPages}
          width="65px"
          size="sm"
        />
      </HStack>
      <HStack spacing="4px">
        <Text fontSize="sm">
          Showing {startItem.toLocaleString()}-{endItem.toLocaleString()} of{" "}
          {totalItems.toLocaleString()}
        </Text>
        <Text fontSize="sm">
          --- Page {currentPage} of {totalPages}
        </Text>
      </HStack>
    </HStack>
  );
};

export default DataTable;
