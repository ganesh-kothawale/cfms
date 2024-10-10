package `in`.porter.cfms.domain.exceptions

class FranchiseAlreadyExistsException(code: String) :
  FranchiseException("Franchise with Franchise Id $code already exists")
