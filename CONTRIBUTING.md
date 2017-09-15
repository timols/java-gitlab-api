
This is a copy of Lukas Eder's JOOq Contributing.md guide - I'll be modiying it for this repository shortly.
Credit for original goes to Lukas Eder's Jooq CONTRIBUTING.md file.  Original can be found at: https://raw.githubusercontent.com/jOOQ/jOOQ/master/CONTRIBUTING.md

The ideal PR
============

Thanks for offering your help.

In order to make our cooperation as smooth as possible, we have a couple of guidelines that we'd like you to follow:

- If in doubt, please discuss your ideas first before providing a pull request. This often helps avoid a lot of unnecessary work.
- Fork the repository.
- Check out and work on your own fork.
- Try to make your commits as atomic as possible. Related changes to three files should be committed in one commit.
- The commit message should reference the issue number, e.g. `[#5873] Add Setting.quoteEscaping to allow for alternative escaping of single quotes`.
- Try not to modify anything unrelated, i.e. don't make unimportant whitespace / formatting changes, which will just distract during review.
- Please do not remove excess whitespace / unnecessary imports etc.  We'll do this before every release cycle.
- DO NOT CHANGE existing API calls method names, signatures.  If necessary please add a new signature or overload.
- Most changes should include Test classes (JUNIT4).
- Be sure you agree to transfer your rights to us (see below) before contributing.

Contributing to Open Source
=========================================

Thank you very much for contributing to java-gitlab-api

Thank you again very much for your contribution
