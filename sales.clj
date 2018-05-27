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
	(println (apply str (for [sales salesData] 
		(str 
			(get sales 0) 
			":"  
			(vec [(getCustNameById (get sales 1) custData) (getProdNameById (get sales 2) prodData) (get sales 3)])
			"\n"
		)
	)))
)

(defn operations [userOption custData prodData salesData]
	(println)
	(cond 
    (= userOption "1") (displayCustomerTable custData)
    (= userOption "2") (displayProductTable prodData)
    (= userOption "3") (displaySalesTable custData prodData salesData)
    (= userOption "4") (println "Option 4")
    (= userOption "5") (println "Option 5")
    (= userOption "6") [(println "Good bye")(. System exit 0)]
    :else "Invalid option...")
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
	;;(menu custData prodData salesData)
)

(main)
