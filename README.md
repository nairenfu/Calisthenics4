# Calisthenics4
Project 4.4. I'm getting there, *trust me*. Started from other direction this time: from starting a workout, back towards database integration.

Learning Points:
1. Is this the fabled rapid prototyping? Feels *not* rapid as all hell.
2. Design decisions: 
- Should I keep a local copy of everything, or do lookups as needed? (Chose lookups because local copies require passing lots of objects around - boilerplate headache...)
- Lookups are asynchronus: So how and where (Activity, Fragment, Adapter) should I do the lookup? (Chose activity - less methods to implement, less fragmented)
- How do you know all asynchronus tasks are done (successfully (easy, onSuccess)) so that the activity can proceed? (Chose to compare size of uniqueExercises array, and exerciseIdExercise map, since it's a 1-1 correlation. No need to check if both is a subset of each other since I *can* (but not yet) programatically guarantee the correlation (TODO Not fully implemented, have not done onFailure/does not exist checks).
3. Database integration (specifically FirestoreFirebase) and callback methods (interface)
4. RecyclerView, CardView and SwipePagerAdapter (I will remember them without needing to look them up *some day*)

To Do:
1. TONS
2. UX...
3. Find bugs
4. Fix bugs
5. Tons more
