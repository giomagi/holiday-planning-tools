Generator of flight combinations.

# Features

### Completed
* get all flight combinations (direct only) between two airports on a given day from file based data
* extend to a range of days (e.g. earlierStart 1/4/2017, latestReturn 30/5/2017)
* return A/R combinations
* get flights between two sets of endpoints (e.g. (Heathrow, Gatwick) to (Bilbao, San Sebastian, Bordeaux))
* enforce a minimum stay in number of holidays (e.g. holidaysOffWork 3-5)

### Planned
* endpoints can be specified either as cities or airports
* include only flights in a certain time window (e.g. leaving after 10:30 and landing before 18:30 - local times)
* get data from ???
* allow specification of passengers
* currency handling