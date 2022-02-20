As an investment bank we have the requirement to maintain the total quantity of a traded security[1] held at any point in time (this is referred as real time position).

All the positions are stored in a Position Book, aggregated using the trading account and the security identifier.
The Position Book also stores the details of all the events involving the trading account and security.
The position book is updated whenever a Trade Event comes into the system; a trade event can represent an operation to buy a security, sell a security or cancel a previously issued event
A trade event always carries a ID and the number of securities to trade (buy or sell). 
A cancel event is meant to cancel any trade event with the same event ID; as a consequence, the quantity and security Id of a cancellation event are meaningless.
