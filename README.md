# CSY2028 Systems Design & Development - Assignment 02 (Branoperty)

Branoperty is a small, lightweight and open-source application designed for estate agencies. It provides a comprehensive branch and property management system.

Branoperty - an amalgamation of the word **branch** and **property**. _Clever, I know!_

## Disclaimer

This project is not suited for production. This is entirely for assignment/demonstration purposes.

## How it Works

The software is designed to service two users; an administrator and branch secretaries. The administrator mainly managed branches (can also manage properties of all branches) and branch secretaries can manage properties for their branch.

The administrator has the ability to create, read (view), update and delete (CRUD) branches and properties. Each branch secretary has the ability to create, read (view), update and delete (CRUD) properties - which are associated to their branch.

It has a simple an intuitive user interface (UI) which any user will be able to understand and operate. The application comes with auto-search, keyboard-only operation and more.

### Admin Login Details

- Username: admin
- Password: password

## Technical Details

The software is built using Java SE version (v14.0.2) using the open-source JavaFX (v14.0.2.1) framework for the GUI. Gluon's SceneBuilder application was also used to quickly develop a GUI given the timely nature of the project.

The source-code follows the model view controller (MVC) design pattern and uses Java's Serialization API to store the data on disk.

## Gallery

![Branoperty Login Screen](https://cdn.jenewland.me.uk/media/images/branoperty-login.png)

![Branoperty Dashboard (Branches)](https://cdn.jenewland.me.uk/media/images/branoperty-dashboard-branches.png)

![Branoperty Dashboard (Properties)](https://cdn.jenewland.me.uk/media/images/branoperty-dashboard-properties.png)

![Branoperty Dashboard (Branches)](https://cdn.jenewland.me.uk/media/images/branoperty-property-update.png)

![Branoperty Create Branch](https://cdn.jenewland.me.uk/media/images/branoperty-branch-create.png)

## Known Bugs

For known issues (bugs) see the issues tab.

## Improvements

For improvements/suggestions see the issues tab.

---

Copyright © 2020 Jordan Newland.
