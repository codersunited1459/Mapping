________________________________________
✅ Step 1: What is Association in Spring Boot / JPA?
In real life, entities are related:
•	A Person has an AadharCard
•	A User has a Profile
•	An Employee has a Locker
These relationships are called Associations in JPA.
________________________________________
✅ Types of Associations in JPA
There are 4 main types:
Mapping	Meaning
OneToOne	One A → One B
OneToMany	One A → Many B
ManyToOne	Many A → One B
ManyToMany	Many A → Many B
________________________________________









============================================================================================================================================

	One-to-One Mapping (Unidirectional)
We’ll learn:
•	What it means
•	How DB looks
•	How to code it
•	How saving works
________________________________________

	What is One-to-One Mapping?
Simple Meaning:
👉 One record in table A is linked to exactly ONE record in table B.
________________________________________
📌 Real Example
Person → AadharCard
•	One person has one card
•	One card belongs to one person
________________________________________
	What is Unidirectional?
🔹 Unidirectional = Only ONE side knows the relation
Meaning:
👉 Person knows about AadharCard
👉 But AadharCard DOES NOT know about Person
________________________________________
Visualization
Person  ------>  AadharCard
Only one arrow.
________________________________________
	How Database Looks
Let’s see tables.
________________________________________
Person Table
id	name	card_id
1	Raghav	10
________________________________________
AadharCard Table
id	number
10	1234-5678
________________________________________
👉 Notice:
•	Foreign key lives in Person table
•	Because Person owns the relationship
________________________________________
	Step 5: JPA Annotations Needed
For unidirectional OneToOne:
We use:
@OneToOne
@JoinColumn
________________________________________







	Complete Example (Simple)
________________________________________
📌 Entity 1: AadharCard
import jakarta.persistence.*;

@Entity
@Table(name = "aadhar_card")
public class AadharCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true)
    private String number;

    // getters & setters
}

📌
Entity 2: Person (Owning Side)
import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name", nullable = false)
    private String name;

@OneToOne(
optional = false,
cascade = {
CascadeType.ALL
},
fetch = FetchType.LAZY,
orphanRemoval = true,
targetEntity = AadharCard.class
)
@JoinColumn(
name = "card_id",
referencedColumnName = "id",
nullable = false,
unique = true,
updatable = true,
insertable = true,
foreignKey = @ForeignKey(
name = "fk_person_aadhar",
value = ConstraintMode.CONSTRAINT
),
columnDefinition = "BIGINT"
)
private AadharCard aadharCard;

    // getters & setters
}
________________________________________




===========================
✅ All Parameters of @OneToOne
There are 6 important parameters:
1️⃣ mappedBy
2️⃣ cascade
3️⃣ fetch
4️⃣ optional
5️⃣ orphanRemoval
6️⃣ targetEntity


✅ What is cascade in JPA?
Cascade means:
When I perform an operation on the parent entity, should the same operation automatically happen to the related child entity?
It controls whether operations like save, update, delete should flow from parent → child.
________________________________________
🧠 Simple Example
Imagine:
•	User → Parent
•	Profile → Child
@OneToOne(cascade = CascadeType.ALL)
private Profile profile;
Now let’s understand what this really does.
________________________________________
🔹 Without Cascade
If you create both objects:
Profile profile = new Profile();
User user = new User();
user.setProfile(profile);

userRepository.save(user);
❌ You may get an exception:
TransientPropertyValueException
Why?
Because:
•	profile is new
•	It is not saved in the database
•	Hibernate doesn’t automatically save it
So you must manually save:
profileRepository.save(profile);
userRepository.save(user);
________________________________________
🔹 With Cascade
If you use:
@OneToOne(cascade = CascadeType.PERSIST)
Now:
userRepository.save(user);
✔ User gets saved
✔ Profile also gets saved automatically
No manual save needed.
________________________________________
📌 Types of Cascade
1️⃣ PERSIST
Save parent → Save child
2️⃣ MERGE
Update parent → Update child
3️⃣ REMOVE
Delete parent → Delete child
4️⃣ REFRESH
Reload parent → Reload child
5️⃣ DETACH
Detach parent → Detach child


6️⃣ ALL
Does everything above
cascade = CascadeType.ALL
________________________________________
🎯 Important Rules
✔ Cascade works from parent → child only
✔ It works on the owning side
✔ It prevents “unsaved transient instance” errors
✔ It reduces manual repository calls
________________________________________
🚨 When Should You Use Cascade?
Use cascade when:
•	Child cannot exist without parent
•	Lifecycle of child depends on parent
Example:
•	User → Profile
•	Order → OrderItems
Do NOT use cascade when both entities are independent.
________________________________________
🧾 One-Line Definition
Cascade tells JPA to automatically apply parent entity operations to its related child entity.
===========================
✅ 1. What is Fetch in JPA?
🔹 Simple Meaning
Fetch defines:
WHEN related entity data should be loaded from database
NOT how — but when.
________________________________________
🔹 Example
You fetch a Person.
Question:
👉 Should AadharCard also be loaded immediately?
OR
👉 Should it load only when accessed?
This is controlled by fetch type.
________________________________________
✅ 2. Types of Fetch
There are ONLY TWO types:
Fetch Type	Meaning
EAGER	Load immediately
LAZY	Load only when needed
________________________________________


✅ 3. FetchType.EAGER
🔹 Meaning
Related entity loads immediately along with parent.
Example
@OneToOne(fetch = FetchType.EAGER)
private AadharCard aadharCard;

What Happens Internally
When you fetch Person:
Person p = personRepo.findById(1);
Hibernate runs:
SELECT * FROM person WHERE id=1;
SELECT * FROM aadhar_card WHERE id=10;
OR sometimes:
JOIN query

🔹 Default Behavior
Relationship	Default Fetch
OneToOne	EAGER
ManyToOne	EAGER

Pros
✔ Easy to use
✔ No lazy loading errors
✔ Data always available
Cons (VERY IMPORTANT)
❌ Loads unnecessary data
❌ More DB queries
❌ Performance issues in large apps
________________________________________
✅ 4. FetchType.LAZY
🔹 Meaning
Child entity loads ONLY when accessed
Example
@OneToOne(fetch = FetchType.LAZY)
private AadharCard aadharCard;

What Happens Internally
When fetching Person:
SELECT * FROM person WHERE id=1;
Only Person loads.

When You Access Child
person.getAadharCard();
Hibernate runs:
SELECT * FROM aadhar_card WHERE id=10;

🔹 How LAZY Works Internally
Hibernate creates a proxy object instead of loading data immediately.
Think of it like:
Placeholder → loads real data when accessed

Default Behavior
Relationship	Default Fetch
OneToMany	LAZY
ManyToMany	LAZY
________________________________________

✅ 5. Visual Difference
________________________________________
EAGER
Fetch Person
↓
Fetch AadharCard immediately
________________________________________
LAZY
Fetch Person
↓
Create Proxy
↓
Load AadharCard only when accessed
________________________________________
✅ 6. Most Common Problem — LazyInitializationException
When Does It Occur?
When:
👉 You try to access LAZY field after session is closed

Example
Person p = personRepo.findById(1).get();

// session closed here

p.getAadharCard();  // ❌ ERROR
Error:
LazyInitializationException:
could not initialize proxy

Why It Happens?
Because:
👉 Hibernate needs active DB session to fetch LAZY data.
________________________________________
✅ 7. How to Fix LazyInitializationException

Solution 1 — Use Transaction
Most common.
@Transactional
public Person getPerson() {
return personRepo.findById(1).get();
}

Solution 2 — Use JOIN FETCH Query
@Query("SELECT p FROM Person p JOIN FETCH p.aadharCard WHERE p.id = :id")

Solution 3 — Use DTO Projection
Best for real applications.
________________________________________
❗ Important Note About OneToOne LAZY
JPA spec says:
👉 OneToOne LAZY is optional
Hibernate supports it, but sometimes may behave like EAGER unless bytecode enhancement is enabled.
So in practice:
@OneToOne(fetch = FetchType.LAZY)
works fine in modern Spring Boot.
________________________________________



✅ 8. Performance Impact (VERY IMPORTANT)

EAGER Example Problem
Imagine:
•	1000 Persons
•	Each has AadharCard
Fetching all persons:
1000 queries for cards!
This causes:
👉 N+1 Query Problem

LAZY Solves This
Loads only when needed.
________________________________________
🧠 Golden Rule in Real Projects
👉 ALWAYS use LAZY by default.
Only use EAGER when:
✔ You ALWAYS need child data
✔ Relationship is very small
________________________________________
✅ 9. Real-World Recommended Mapping
@OneToOne(
fetch = FetchType.LAZY,
cascade = CascadeType.ALL,
optional = false
)
________________________________________

🧠 Interview Cheat Sheet
If asked:
"What is fetch type?"
Answer:
Fetch type defines when related entity should be loaded from database.

"Default fetch type of OneToOne?"
Answer:
EAGER.

"Which fetch type is better?"
Answer:
LAZY for performance.
________________________________________
✅ 10. Quick Comparison Table
Feature	EAGER	LAZY
Load Time	Immediately	On demand
Performance	Slower	Faster
Default in OneToOne	Yes	No
Risk of Exception	Low	Possible
Recommended Usage	Rare	Most cases
________________________________________







===============================================================

✅ 1. What is mappedBy? (Simple Definition)
mappedBy tells JPA:
“This side does NOT own the relationship. The foreign key is managed by another entity.”
In short:
👉 It marks the inverse (non-owning) side of a bidirectional relationship.
________________________________________
✅ 2. Why Do We Need mappedBy?
Because in bidirectional mapping, both entities reference each other.
Example:
Person  <---->  AadharCard
Now JPA asks:
👉 “Which side should create the foreign key column?”
We must clarify this.
So:
•	One side = Owning side → uses @JoinColumn
•	Other side = Inverse side → uses mappedBy
________________________________________
✅ 3. Owning Side vs Inverse Side (Core Idea)
🔹 Owning Side
This side:
✔ Contains the foreign key
✔ Controls DB relationship
✔ Uses @JoinColumn
✔ Saves/updates relationship

🔹 Inverse Side
This side:
✔ Does NOT contain foreign key
✔ Only reflects relationship
✔ Uses mappedBy
✔ Cannot update DB relationship
________________________________________
✅ 4. Example — Bidirectional OneToOne

Owning Side (Person)
@Entity
public class Person {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "card_id")
    private AadharCard aadharCard;
}
This side:
👉 Creates FK column
👉 Controls relationship

Inverse Side (AadharCard)
@Entity
public class AadharCard {

    @Id
    private Long id;

    @OneToOne(mappedBy = "aadharCard")
    private Person person;
}
________________________________________
✅ 5. What Does mappedBy = "aadharCard" Mean?
It means:
👉 The relationship is already mapped by the field:
Person.aadharCard
So:
👉 Do NOT create another FK column.
________________________________________
✅ 6. What Happens Internally?
Without mappedBy:
JPA thinks both sides own relationship.
So it creates:
❌ Two foreign keys
OR
❌ A join table
Which is wrong.

With mappedBy:
JPA knows:
👉 Only Person owns relationship
👉 Only one FK is created
________________________________________





✅ 7. Database Structure
After using mappedBy:
________________________________________
Person Table
id	card_id
1	10
________________________________________
AadharCard Table
id
10
No FK in AadharCard table.
________________________________________
✅ 8. VERY IMPORTANT RULE
⭐ Only Owning Side Updates Relationship
This is the MOST important rule.
Correct Way
person.setAadharCard(card);
personRepo.save(person);
Relationship saved.

Wrong Way
card.setPerson(person);
aadharRepo.save(card);
Nothing happens ❌
Because inverse side cannot update DB.
________________________________________

✅ 9. Common Beginner Mistake
Thinking both sides can update relationship.
No.
Only owning side controls DB.
________________________________________
✅ 10. When Do We Use mappedBy?
Use only in:
✔ Bidirectional relationships
Not used in:
❌ Unidirectional mappings
________________________________________
✅ 11. Works in All Relationship Types
mappedBy is used in:
•	OneToOne
•	OneToMany
•	ManyToMany
🧠 Example in OneToMany
@OneToMany(mappedBy = "person")
private List<Address> addresses;
Means:
Address table has FK.
________________________________________




✅ 12. Default Value
mappedBy = ""
If empty:
👉 That side becomes owning side.
________________________________________
🧠 Easy Memory Trick
@JoinColumn → owns relationship
mappedBy → mirrors relationship
________________________________________
✅ 13. Real-World Analogy
Think like:
Husband & Wife
•	Husband keeps marriage certificate → owning side
•	Wife just references marriage → inverse side
Certificate = Foreign Key.
________________________________________
✅ 14. Interview One-Line Answer
mappedBy is used in bidirectional relationships to specify the inverse side and indicate that the foreign key is managed by the owning side.
________________________________________
✅ 15. Quick Summary
Feature	Owning Side	Inverse Side
Has FK	Yes	No
Annotation	@JoinColumn	mappedBy
Controls DB	Yes	No
Updates relation	Yes	No
________________________________________



================================================
✅ What is optional in @OneToOne?
🔹 Simple Meaning
optional tells JPA:
Whether the relationship is mandatory or can be NULL
In simple words:
👉 Can this entity exist without the related entity?
✅ Syntax
@OneToOne(optional = true/false)

✅ Default Value
optional = true
Meaning:
👉 Relationship is optional by default.
👉 Foreign key column can be NULL.
✅ Example — optional = true (Default)
@OneToOne(optional = true)
@JoinColumn(name = "card_id")
private AadharCard aadharCard;
What It Means
✔ Person CAN exist without AadharCard
✔ card_id column can be NULL
________________________________________


DB Example
id	name	card_id
1	Raghav	NULL
This is valid.
________________________________________
✅ Example — optional = false
@OneToOne(optional = false)
@JoinColumn(name = "card_id")
private AadharCard aadharCard;
________________________________________
What It Means
👉 Relationship is mandatory
✔ Person MUST have AadharCard
✔ Foreign key cannot be NULL
________________________________________
DB Constraint Created
card_id BIGINT NOT NULL
________________________________________
🧠 Easy Rule to Remember
optional = true  → FK can be NULL
optional = false → FK cannot be NULL
________________________________________
❗ Important Note
optional = false affects:
✔ JPA validation
✔ Database NOT NULL constraint
But it does NOT:
❌ Automatically create the child entity
You still need to set it manually.
________________________________________
🧠 Interview One-Line Answer
optional specifies whether the relationship is mandatory; if set to false, the foreign key cannot be null.
________________________________________
✅ When to Use optional = false?
Use when child is required.
Examples:
•	Person → AadharCard
•	User → Profile
•	Order → Payment
________________________________________
✅ When to Keep optional = true?
Use when child is not always required.
Examples:
•	Employee → ParkingSlot
•	Student → Locker
________________________________________
✅ Quick Summary
Value	Meaning
true (default)	Relationship optional
false	Relationship mandatory
________________________________________







===========================================

✅ What is orphanRemoval?
🔹 Simple Definition
orphanRemoval = true means:
If a child entity is removed from the parent relationship, it will be automatically deleted from the database.
In simple words:
👉 If child becomes “orphan” → delete it.

✅ Syntax
@OneToOne(orphanRemoval = true)

✅ Example (OneToOne)
@OneToOne(
cascade = CascadeType.ALL,
orphanRemoval = true
)
@JoinColumn(name = "card_id")
private AadharCard aadharCard;
________________________________________
✅ What Happens in Practice?
Step 1 — Initially
Person has AadharCard:
Person	card_id
1	10
AadharCard
10
________________________________________

Step 2 — Remove Relationship
person.setAadharCard(null);
personRepository.save(person);
________________________________________
What Happens?
✔ FK becomes NULL
✔ Hibernate deletes the AadharCard record
DELETE FROM aadhar_card WHERE id = 10;
________________________________________
✅ If orphanRemoval = false (default)
@OneToOne(orphanRemoval = false)
Then:
person.setAadharCard(null);
Only FK becomes NULL.
❌ Child record is NOT deleted.
It stays in database.
________________________________________
✅ Difference Between Cascade REMOVE and orphanRemoval
This is VERY important 🔥
________________________________________
🔹 CascadeType.REMOVE
Deletes child when parent is deleted.
Example:
personRepository.delete(person);
→ Child deleted.
________________________________________

🔹 orphanRemoval
Deletes child when relationship is broken.
Example:
person.setAadharCard(null);
→ Child deleted.
________________________________________
✅ Visual Comparison
Action	Cascade REMOVE	orphanRemoval
Delete parent	Deletes child	Deletes child
Remove relationship	❌ No	✔ Yes
________________________________________
✅ When Should You Use orphanRemoval?
Use when:
✔ Child cannot exist independently
✔ Child lifecycle fully depends on parent
Examples:
•	User → Profile
•	Order → OrderItems
•	Person → Passport
________________________________________
❌ When NOT to Use
Do NOT use when:
•	Child can exist alone
•	Child shared by multiple parents
Example:
•	Student → Course
•	Product → Category
________________________________________
🧠 Important Rule
orphanRemoval works only when:
✔ Relationship is managed properly
✔ Entity is inside persistence context
✔ Change is saved within transaction
________________________________________
🧠 Interview One-Line Answer
orphanRemoval automatically deletes a child entity when it is removed from the parent relationship.
________________________________________
✅ Default Value
orphanRemoval = false
________________________________________
✅ Quick Summary
Parameter	Purpose
orphanRemoval = true	Delete child when relationship removed
Default	false
Works With	OneToOne & OneToMany
________________________________________










===============================================
✅ What is targetEntity?
🔹 Simple Definition
targetEntity tells JPA:
Which entity class this relationship refers to
In simple words:
👉 It explicitly specifies the related entity type.

✅ Syntax
@OneToOne(targetEntity = AadharCard.class)
________________________________________
✅ Do We Normally Use It?
👉 Almost never in normal cases.
Why?
Because JPA automatically detects the target entity from the field type.
________________________________________
✅ Normal Example (Without targetEntity)
@OneToOne
@JoinColumn(name = "card_id")
private AadharCard aadharCard;
Here:
👉 JPA already knows the target entity is AadharCard
So targetEntity is not needed.
________________________________________

✅ When is targetEntity Used?
It is mainly used when:
🔹 1. Using Interface Type
If your field is declared as an interface:
private Document document;
JPA cannot determine the actual entity class.
So you must specify:
@OneToOne(targetEntity = Passport.class)
private Document document;
Now JPA knows:
👉 The actual entity class is Passport.
________________________________________
🔹 2. Generics Case
When using generics in base classes:
public abstract class BaseEntity<T> {
@OneToOne(targetEntity = AadharCard.class)
private T entity;
}
________________________________________
✅ Default Value
targetEntity = void.class
Meaning:
👉 JPA will infer the entity from the field type.
________________________________________



🧠 Important Note
In 95% of real Spring Boot applications:
You will NOT use targetEntity.
Because:
private AadharCard aadharCard;
is already clear enough.
________________________________________
🧠 Interview One-Line Answer
targetEntity explicitly specifies the related entity class when JPA cannot infer it automatically.
________________________________________
✅ Quick Summary
Property	Purpose
targetEntity	Specifies related entity class
Default	Inferred from field type
Common Usage	Rare
Used In	Interface or generic scenarios
________________________________________
🚀 Now You Have Covered ALL @OneToOne Parameters
You now understand:
•	mappedBy
•	cascade
•	fetch
•	optional
•	orphanRemoval
•	targetEntity
________________________________________






======================================================
✅ 1. What is @JoinColumn?
🔹 Simple Definition
@JoinColumn tells JPA:
Which column should store the foreign key for a relationship
It is always used on the owning side.

Example
@OneToOne
@JoinColumn(name = "card_id")
private AadharCard aadharCard;
Means:
👉 Create column card_id in Person table
👉 Store AadharCard ID inside it
________________________________________
✅ 2. All Important Parameters of @JoinColumn
There are 8 main parameters:
1️⃣ name
2️⃣ referencedColumnName
3️⃣ nullable
4️⃣ unique
5️⃣ insertable
6️⃣ updatable
7️⃣ foreignKey
8️⃣ columnDefinition
Let’s go one by one.



________________________________________
🔹 1. name
⭐ MOST USED PARAMETER
What It Means
Specifies the foreign key column name.
Example
@JoinColumn(name = "card_id")

Default Behavior
If not specified:
JPA generates:
fieldName_primaryKey
Example:
aadhar_card_id

When to Use
👉 Always use it to control DB naming.
________________________________________
🔹 2. referencedColumnName
What It Means
Specifies:
Which column of the target entity is referenced.
Default Value
Primary key of target entity

Example
@JoinColumn(
name = "card_number",
referencedColumnName = "number"
)
Meaning:
FK refers to number column instead of ID.

When to Use
Rare cases:
✔ When referencing non-PK column
________________________________________
🔹 3. nullable
What It Means
Specifies whether FK column can be NULL.

Example
@JoinColumn(nullable = false)
Meaning:
👉 Relationship is mandatory.

Default Value
nullable = true

DB Effect
Creates:
NOT NULL constraint
________________________________________
🔹 4. unique
What It Means
Specifies:
Whether FK column must be unique.

Example
@JoinColumn(unique = true)

When Used
Mostly in:
👉 OneToOne mapping
Because:
One child belongs to only one parent.

Default Value
unique = false
________________________________________
🔹 5. insertable
What It Means
Specifies whether FK column is included in INSERT statements.
Example
@JoinColumn(insertable = false)
Meaning:
JPA will not insert value.

When Used
Rare cases:
✔ Read-only relationships
✔ DB-managed columns

Default
insertable = true
________________________________________
🔹 6. updatable
What It Means
Specifies whether FK column can be updated.
Example
@JoinColumn(updatable = false)
Meaning:
FK cannot change after creation.
Use Case
Immutable relationships.
Default
updatable = true
________________________________________
🔹 7. foreignKey
What It Means
Defines custom foreign key constraint.
Example
@JoinColumn(
foreignKey = @ForeignKey(name = "fk_person_card")
)

What It Does
Creates DB constraint:
CONSTRAINT fk_person_card FOREIGN KEY

Default
JPA auto-generates FK name.
________________________________________
🔹 8. columnDefinition
What It Means
Allows custom SQL column definition.
Example
@JoinColumn(columnDefinition = "BIGINT NOT NULL")

When Used
Rare cases:
✔ Custom DB types
✔ Vendor-specific definitions
Default
JPA generates column definition automatically.
________________________________________
✅ 3. Full Example Using All Parameters
@OneToOne
@JoinColumn(
name = "card_id",
referencedColumnName = "id",
nullable = false,
unique = true,
insertable = true,
updatable = true,
foreignKey = @ForeignKey(name = "fk_person_card"),
columnDefinition = "BIGINT"
)
private AadharCard aadharCard;
________________________________________
✅ 4. Most Common Real-World Usage
In real projects we typically use:
@JoinColumn(
name = "card_id",
nullable = false,
unique = true
)
That’s it.
________________________________________
🧠 Easy Memory Trick
@JoinColumn controls FK column properties
Think:
Name + Constraints + Reference
________________________________________
🧠 Interview One-Line Answer
@JoinColumn specifies the foreign key column in the owning entity and allows customization of its name, constraints, and reference.
________________________________________
✅ Quick Summary Table
Parameter	Purpose	Default
name	FK column name	Auto-generated
referencedColumnName	Target column referenced	PK
nullable	FK can be NULL	true
unique	FK must be unique	false
insertable	Include in INSERT	true
updatable	Allow updates	true
foreignKey	Custom FK constraint	Auto
columnDefinition	Custom SQL definition	Auto
________________________________________

