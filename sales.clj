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

(defn operations [userOption custData prodData salesData]
	(println)
	(cond 
    (= userOption "1") (displayCustomerTable custData)
    (= userOption "2") (println "Option 2")
    (= userOption "3") (println "Option 3")
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
	
	(while true (menu custData prodData salesData))
	;;(menu custData prodData salesData)
)

(main)
