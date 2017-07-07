package com.bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BankController {
	ArrayList<Customer> myCustomerArrayList = new ArrayList<Customer>();
	int customerCounter = 0;
	// Scanner sc1 = new Scanner(System.in);

	// public void readFile_version2() throws IOException {
	//
	// FileReader myReader = new FileReader("temp3.txt");
	// BufferedReader in = new BufferedReader(myReader);
	//
	// String line;
	//
	// while ((line = in.readLine()) != null) {
	// // System.out.println(line);
	//
	// Customer cust = new Customer();
	// StringTokenizer st = new StringTokenizer(line, "|");
	// while (st.hasMoreTokens()) {
	//
	// cust.setCustomerName(st.nextToken());
	// cust.setCustomerAccno(st.nextToken());
	// cust.setCustomerDOB(st.nextToken());
	// cust.setCustomerAddress(st.nextToken());
	// cust.setCustomerPAN(st.nextToken());
	// cust.setCustomerAdhar(st.nextToken().trim());
	// cust.setMoney(st.nextToken().trim());
	// cust.setMobNo(st.nextToken().trim());
	// }
	// myCustomerArrayList.add(cust);
	//
	// // myCustomerArraylist.add(customerCounter) = cust;
	// customerCounter++;
	//
	// }
	//
	// in.close();
	// // System.out.println(customerCounter);
	// }
	/*
	 * 
	 * REST - Representational State transfer Client - Server architecture
	 * 
	 * CRUD - Create Read Update Delete
	 * 
	 * User - Account
	 * 
	 * HTTP
	 * 
	 * GET - Read POST- Create PUT - Update DELETE - Delete
	 * 
	 * https://spring.io/guides/gs/rest-service/
	 */

	@GetMapping
	public ArrayList<Customer> getUser(@RequestParam(value = "acno", required = false) String acno) {// Welcome

		if (acno == null) {

			// I returned arraylist in delete method to returmn allaccount
			// records

			return myCustomerArrayList;

		}

		String leftTrimedacno = validateGetUser(acno);

		if (leftTrimedacno == null) {
			throw new RuntimeException("Account number received is not valid. Please provide correct account number");
		}

		try {
			for (int i = 0; i < myCustomerArrayList.size(); i++) {
				if (myCustomerArrayList.get(i).getCustomerAccno().equals(leftTrimedacno)) {
					ArrayList<Customer> returnList = new ArrayList<>();
					returnList.add(myCustomerArrayList.get(i));
					return returnList;

					/*
					 * return "The Account Number" + leftTrimedacno +
					 * " Has Following Data:  \n \nName: " +
					 * myCustomerArrayList.get(i).getCustomerName() + "\nDob: "
					 * + myCustomerArrayList.get(i).getCustomerDOB() +
					 * "\nAddress: " +
					 * myCustomerArrayList.get(i).getCustomerAddress() +
					 * "\nAdhar: " +
					 * myCustomerArrayList.get(i).getCustomerAdhar() +
					 * "\nMoney: " + myCustomerArrayList.get(i).getMoney() +
					 * "\nMobileNumber: " +
					 * myCustomerArrayList.get(i).getMobNo();
					 */

				}

			}

			throw new RuntimeException("Account Not Present in Database or deleted from database");
		} catch (Exception e) {
			throw new RuntimeException("Account Not Present in Database or deleted from database");
		}
	}

	// @GetMapping
	public ArrayList<String> getUserString(@RequestParam(value = "acno", required = false) String acno) {// Welcome

		if (acno == null) {

			// I returned arraylist in delete method to returmn allaccount
			// records

			ArrayList<String> returnList = new ArrayList<>();
			for (int i = 0; i < myCustomerArrayList.size(); i++) {
				returnList.add(reformatCustomer(myCustomerArrayList.get(i)));
			}
			return returnList;

		}

		String leftTrimedacno = validateGetUser(acno);

		if (leftTrimedacno == null) {
			throw new RuntimeException("Account number received is not valid. Please provide correct account number");
		}

		try {
			for (int i = 0; i < myCustomerArrayList.size(); i++) {
				if (myCustomerArrayList.get(i).getCustomerAccno().equals(leftTrimedacno)) {

					ArrayList<String> returnArrayList = new ArrayList<>();
					String customerString = reformatCustomer(myCustomerArrayList.get(i));
					returnArrayList.add(customerString);
					return returnArrayList;
					/*
					 * return "The Account Number" + leftTrimedacno +
					 * " Has Following Data:  \n \nName: " +
					 * myCustomerArrayList.get(i).getCustomerName() + "\nDob: "
					 * + myCustomerArrayList.get(i).getCustomerDOB() +
					 * "\nAddress: " +
					 * myCustomerArrayList.get(i).getCustomerAddress() +
					 * "\nAdhar: " +
					 * myCustomerArrayList.get(i).getCustomerAdhar() +
					 * "\nMoney: " + myCustomerArrayList.get(i).getMoney() +
					 * "\nMobileNumber: " +
					 * myCustomerArrayList.get(i).getMobNo();
					 */

				}

			}

			throw new RuntimeException("Account Not Present in Database or deleted from database");
		} catch (Exception e) {
			throw new RuntimeException("Account Not Present in Database or deleted from database");
		}
	}

	private String reformatCustomer(Customer customer) {
		return "The Account Number" + customer.getCustomerAccno() + " Has Following Data:  Name: "
				+ customer.getCustomerName() + "Dob: " + customer.getCustomerDOB() + "Address: "
				+ customer.getCustomerAddress() + "Adhar: " + customer.getCustomerAdhar() + "Money: "
				+ customer.getMoney() + "MobileNumber: " + customer.getMobNo();

	}

	private String validateGetUser(String acno) {
		String rightTrimedacno = acno.trim();
		String leftTrimedacno = rightTrimedacno.replaceAll("^\\s+", "");

		if (!leftTrimedacno.startsWith("SNGURB")) {
			return null;
		}

		// check if the account number is present in database
		// return the account number if present
		// return null otherwise
		String validatedAcno = checkIfAccountNumberIsPresent(leftTrimedacno);
		return validatedAcno;
	}

	/**
	 * This method takes the account number String It checks if the account
	 * number exists in database or not It also checks if the boolean flag for
	 * account number is true or not. (It should be true for valid account)
	 * Returns null otherwise
	 * 
	 * @param acno
	 * @return
	 */
	private String checkIfAccountNumberIsPresent(String acno) {
		for (int i = 0; i < myCustomerArrayList.size(); i++) {

			// Check if the account number is valid and matches with the
			// customer Number
			if ((myCustomerArrayList.get(i).isFlag()) && (acno.equals(myCustomerArrayList.get(i).getCustomerAccno()))) {
				return acno;
			}
		}
		return null;
	}

	@PostMapping
	public Customer createAccount(@RequestParam(value = "name") String name, @RequestParam(value = "dob") String dob,
			@RequestParam(value = "address") String address, @RequestParam(value = "adhar") String adhar,
			@RequestParam(value = "money") String money, @RequestParam(value = "mobNo") String mobNo) {
		String customerAccountNumber = "SNGURB-" + (customerCounter);

		try {
			Customer cs = new Customer();
			cs.setCustomerName(name);
			cs.setCustomerAccno(customerAccountNumber);
			cs.setCustomerDOB(dob);
			cs.setCustomerAddress(address);
			// cs.setCustomerPAN(pan);
			cs.setCustomerAdhar(adhar);
			cs.setMoney(money);
			cs.setFlag(true);
			cs.setMobNo(mobNo);
			myCustomerArrayList.add(cs);

			customerCounter++;

			return cs;

			/*
			 * return cs.getCustomerAccno() + cs.getCustomerName() +
			 * cs.getCustomerDOB() + cs.getCustomerAddress() +
			 * cs.getCustomerAdhar() + cs.getMoney() + cs.getMobNo();
			 */
			/*
			 * return "The Account Number" + cs.getCustomerAccno() +
			 * " has been created with Following Data:  \nName: " +
			 * cs.getCustomerName() + "\nDob: " + cs.getCustomerDOB() +
			 * "\nAddress: " + cs.getCustomerAddress() + "\nAdhar: " +
			 * cs.getCustomerAdhar() + "\nMoney: " + cs.getMoney() +
			 * "\nMobileNumber: " + cs.getMobNo() + " \n\tSuccessfully";
			 */

		} catch (ArithmeticException e) {
			System.out.println("Warning: ArithmeticException");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Warning: ArrayIndexOutOfBoundsException");
		} catch (Exception e) {
			System.out.println("Warning: Some Other exception");
		}
		System.out.println("Out of try-catch block...");
		// return customerAccountNumber;
		// return "Post account executed";

		return null;
	}

	@PutMapping

	public Customer updateAccount(@RequestParam(value = "acno") String acno, @RequestParam(value = "name") String name,
			@RequestParam(value = "dob") String dob, @RequestParam(value = "address") String address,
			@RequestParam(value = "adhar") String adhar, @RequestParam(value = "mobNo") String mobNo) {
		try {
			for (int i = 0; i < myCustomerArrayList.size(); i++) {
				if (myCustomerArrayList.get(i).isFlag()
						&& (acno.equals(myCustomerArrayList.get(i).getCustomerAccno()))) {
					Customer cust = myCustomerArrayList.get(i);

					if (cust.getCustomerAccno().equals(acno)) {
						cust.setCustomerAccno(acno);
						cust.setCustomerName(name);
						cust.setCustomerDOB(dob);
						cust.setCustomerAddress(address);
						// cust.setCustomerPAN(pan);
						cust.setCustomerAdhar(adhar);
						cust.setMobNo(mobNo);
						return myCustomerArrayList.get(i);
						/*
						 * "The Account Number" + acno +
						 * " Has been Updated With following   \nName: " + name
						 * + "\nDob: " + dob + "\nAddress: " + address +
						 * "\nAdhar: " + adhar + "\nMobileNumber: " + mobNo +
						 * " \n\tSuccessfully";
						 */

					}
				}
			}
			throw new RuntimeException("Account Not Present in Database or deleted from database");
			// return "Account Not Present in Database or deleted from
			// database";
		} catch (Exception e) {

			throw new RuntimeException("Account Not Present in Database or deleted from database");
			// return "error in updating account" + e;
		}
	}

	@DeleteMapping
	public Customer deleteAccount(@RequestParam(value = "delcust") String delcust) {
		try {
			for (int i = 0; i < myCustomerArrayList.size(); i++) {
				if (myCustomerArrayList.get(i).isFlag()) {
					while (delcust.equals(myCustomerArrayList.get(i).getCustomerAccno())) {

						if (myCustomerArrayList.get(i).getCustomerAccno().equals(delcust)) {

							myCustomerArrayList.get(i).setFlag(false);

							return myCustomerArrayList.get(i);
							// "The Account number :- " + delcust + " has been
							// Deleted Successfully";
						}
					}
				}
			}
			throw new RuntimeException("Account Not Present in Database or deleted from database");
			// return "Account Not Present in Database or deleted from
			// database";

		} catch (Exception e) {
			throw new RuntimeException("Account Not Present in Database or deleted from database");
			// return "Enter Correct Account number";
		}
	}

}
