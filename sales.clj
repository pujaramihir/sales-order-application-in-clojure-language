(ns sales)

(defn readfile 
	[filename]
	(def filedata (slurp filename))
	(vec (for [line (clojure.string/split-lines filedata)] 
		(clojure.string/split (apply str line) #"\|")))
)

(defn displayCustomerTable [custData]
	(def sortedCustData (sort-by first custData))
	(println (apply str (for [cust sortedCustData] (str (str (get cust 0) ":") (subvec cust 1) "\n"))))
	
)

(defn displayProductTable [prodData]
	(def sortedProdData (sort-by first prodData))
	(println (apply str (for [prod sortedProdData] (str (str (get prod 0) ":") (subvec prod 1) "\n"))))
) 

(defn getCustNameById [id custData]
	(apply str (for [cust custData] (cond 
		(= id (get cust 0)) (get cust 1)
		:else ""
	)))
)

(defn getProdNameById [id prodData]
	(apply str (for [prod prodData] (cond 
		(= id (get prod 0)) (get prod 1)
		:else ""
	)))
)

(defn displaySalesTable [custData prodData salesData]
	(def sortedSalesData (sort-by first salesData))
	(println (apply str (for [sales sortedSalesData] 
		(str 
			(get sales 0) 
			":"  
			(vec [(getCustNameById (get sales 1) custData) (getProdNameById (get sales 2) prodData) (get sales 3)])
			"\n"
		)
	)))
)

(defn getCustomerIdByName [name custData]
	(apply str (for [cust custData] 
		(cond
			(= (clojure.string/lower-case name) (clojure.string/lower-case (get cust 1))) (get cust 0)
			:else ""
		)
	))
)

(defn getProductPriceById [id prodData]
	(apply str (for [prod prodData] 
		(cond
			(= id (get prod 0)) (get prod 2)
			:else ""
		)
	))
)

(defn totalSalesForCustomer [custData prodData salesData]
	(println "Enter Customer Name (Case Sensitive): ")
	(def name (read-line))
	(def id (getCustomerIdByName name custData))
	(def prices (for [sales salesData]
		(cond 
			(= id (get sales 1)) (with-precision 2 (* (Double/parseDouble(getProductPriceById (get sales 2) prodData)) (Double/parseDouble (get sales 3))))
			:else 0
		)
	))
	(def totalPrice (reduce + prices))
	(println (str "\n" (getCustNameById id custData) ": $" totalPrice))
)

(defn getProductIdByName [name prodData]
	(apply str (for [prod prodData] 
		(cond
			(= (clojure.string/lower-case name) (clojure.string/lower-case (get prod 1))) (get prod 0)
			:else ""
		)
	))
)

(defn totalCountForProduct [prodData salesData]
	(println "Enter Product Name (Case Sensitive): ")
	(def name (read-line))
	(def id (getProductIdByName name prodData))
	(def count (reduce + (for [sales salesData]
		(cond
			(= id (get sales 2)) (Integer/parseInt (get sales 3))
			:else 0
		)
	)))
	(println (str "\n" name ": " count))
)

(defn operations [userOption custData prodData salesData]
	(println)
	(cond 
    (= userOption "1") (displayCustomerTable custData)
    (= userOption "2") (displayProductTable prodData)
    (= userOption "3") (displaySalesTable custData prodData salesData)
    (= userOption "4") (totalSalesForCustomer custData prodData salesData)
    (= userOption "5") (totalCountForProduct prodData salesData)
    (= userOption "6") [(println "Good bye")(. System exit 0)]
    :else (println "Invalid option..."))
	(println "Press any key to continue .....")
	(read-line)
)

(defn menu [custData prodData salesData]
	(println "*** Sales Menu ***")
	(println "------------------\n")
	(println "1. Display Customer Table")
	(println "2. Display Product Table")
	(println "3. Display Sales Table")
	(println "4. Total Sales for Customer")
	(println "5. Total Count for Product")
	(println "6. Exit\n")
	(println "Enter an option?")
	(def userOption (read-line)) 
	(operations userOption custData prodData salesData)
)

(defn main []
	(def custData (readfile "cust.txt"))
	(def prodData (readfile "prod.txt"))
	(def salesData (readfile "sales.txt"))
	;;(println (getCustNameById "1" custData))
	(while true (menu custData prodData salesData))
	(menu custData prodData salesData)
)

(main)
