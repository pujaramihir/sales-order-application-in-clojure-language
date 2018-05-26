(ns sales)

(defn readfile 
	[filename]
	(def filedata (slurp filename))
	(vec (for [line (clojure.string/split-lines filedata)] 
		(clojure.string/split (apply str line) #"\|")))
)



(defn main []
	(def custData (readfile "cust.txt"))
	(def prodData (readfile "prod.txt"))
	(def salesData (readfile "sales.txt"))
	
	(println (apply str (for [cust custData] (str (str (get cust 0) ":") (subvec cust 1) "\n"))))
)

(main)
